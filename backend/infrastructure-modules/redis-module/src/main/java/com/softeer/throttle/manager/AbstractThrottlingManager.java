package com.softeer.throttle.manager;

import com.softeer.throttle.BackoffStrategy;
import com.softeer.throttle.ThrottlingProperties;
import com.softeer.throttle.ex.ThrottleException;
import com.softeer.throttle.task.RetryTaskWithBucket;
import io.github.bucket4j.*;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

@Slf4j
public abstract class AbstractThrottlingManager {

    private static final int DEFAULT_RETRY_CAPACITY = 5;

    protected final ThrottlingProperties properties;

    private final ProxyManager<String> proxyManager;
    private final BackoffStrategy backoffStrategy;

    private final ScheduledExecutorService retryExecutor;

    protected final AtomicInteger currentTps = new AtomicInteger();
    private final AtomicInteger successCount = new AtomicInteger();

    private final ReentrantReadWriteLock configurationLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock asyncUpdateLock = new ReentrantReadWriteLock();  // 상향 조절 전용 락

    private final AtomicInteger lastUpdatedTps = new AtomicInteger();

    private volatile boolean shutdown = false;
    private Bucket bucket;

    public AbstractThrottlingManager(ProxyManager<String> proxyManager, ThrottlingProperties properties, BackoffStrategy backoffStrategy) {
        this.proxyManager = proxyManager;
        this.properties = properties;
        this.backoffStrategy = backoffStrategy;

        this.retryExecutor = Executors.newScheduledThreadPool(DEFAULT_RETRY_CAPACITY, r -> {
            Thread t = new Thread(r, "throttling-retry-executor-" + System.nanoTime());
            t.setDaemon(true);
            return t;
        });

    }

    @PostConstruct
    public void initialize() {
        currentTps.set(properties.initialTps());
        lastUpdatedTps.set(currentTps.get());
    }

    @PreDestroy
    public void shutdown() {
        log.info("GreedyThrottlingManager 종료 중...");
        shutdown = true;

        // 스케줄러 종료
        retryExecutor.shutdown();
        try {
            if (!retryExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
                retryExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            retryExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("GreedyThrottlingManager 종료 완료");
    }

    public <T> CompletableFuture<T> submit(String key, Supplier<CompletableFuture<T>> task) {
        if (shutdown) {
            CompletableFuture<T> future = new CompletableFuture<>();
            future.completeExceptionally(new RejectedExecutionException("ThrottlingManager is shutting down"));
            return future;
        }

        CompletableFuture<T> future = new CompletableFuture<>();
        RetryTaskWithBucket<T> retryTaskWithBucket = new RetryTaskWithBucket<>(key, task, future, 0);
        executeOrRetry(retryTaskWithBucket);

        return future;
    }

    private <T> void executeOrRetry(RetryTaskWithBucket<T> task) {
        if (shutdown) {
            task.future().completeExceptionally(new RejectedExecutionException("ThrottlingManager is shutting down"));
            return;
        }

        try {
            Bucket bucket = getBucket(task.key());
            ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

            if (probe.isConsumed()) {
                CompletableFuture<T> taskFuture;
                try {
                    taskFuture = task.supplier().get();
                } catch (Throwable t) {
                    handleTaskException(task, t);
                    return;
                }
                taskFuture.whenComplete((result, ex) -> {
                    if (ex != null) handleTaskException(task, ex);
                    else { task.future().complete(result); adjustTps(true); }
                });

            } else {
                long waitMs = TimeUnit.NANOSECONDS.toMillis(probe.getNanosToWaitForRefill());
                long delay = Math.max(1, waitMs);

                retryExecutor.schedule(() -> executeOrRetry(task), delay, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            log.error("executeOrRetry에서 예외 발생. key: {}", task.key(), e);
            task.future().completeExceptionally(e);
        }
    }

    private <T> void handleTaskException(RetryTaskWithBucket<T> task, Throwable ex) {
        Throwable cause = ex;
        if (cause instanceof CompletionException && cause.getCause() != null) {
            cause = cause.getCause();
        }

        if (cause instanceof ThrottleException throttleException) {
            if (throttleException.isRetryable()) {
                log.warn("재시도 가능한 예외 발생. key: {}, attempt: {}, ex: {}", task.key(), task.attempt(), ex.getMessage());
                scheduleRetry(task.withIncrementedAttempt());
            } else {
                log.error("재시도 불가능한 예외. key: {}, attempt: {}", task.key(), task.attempt(), ex);
                task.future().completeExceptionally(ex);
            }
        } else {
            log.error("작업 실행 중 예외 발생. key: {}, attempt: {}", task.key(), task.attempt(), ex);
            task.future().completeExceptionally(ex);
        }
        adjustTps(false);
    }

    private void scheduleRetry(RetryTaskWithBucket<?> task) {
        if (shutdown) {
            task.future().completeExceptionally(new RejectedExecutionException("ThrottlingManager is shutting down"));
            return;
        }

        long delay = backoffStrategy.computeDelay(task.attempt());
        log.info("백오프 재시도 예약. key: {}, attempt: {}, delay: {}ms", task.key(), task.attempt(), delay);

        retryExecutor.schedule(() -> executeOrRetry(task), delay, TimeUnit.MILLISECONDS);
    }

    private void adjustTps(boolean success) {
        int oldTps = currentTps.get();
        int newTps;

        if (success) {
            int successCounter = successCount.incrementAndGet();
            if (successCounter == oldTps) {
                newTps = Math.min(properties.maxTps(), oldTps + 1);

                if (currentTps.compareAndSet(oldTps, newTps)) {
                    successCount.addAndGet(-oldTps);
                    log.info("TPS 상향 조정: {} -> {} ({}회 연속 성공)", oldTps, newTps, successCounter);
                    // 상향 조절 시에는 락 없이 업데이트
                    updateBucketConfigurationAsync();
                }
            }
        } else {
            newTps = Math.max(properties.minTps(), oldTps - properties.failStep());

            if (currentTps.compareAndSet(oldTps, newTps)) {
                successCount.set(0);
                log.info("TPS 하향 조정: {} -> {} (실패로 인한 조정)", oldTps, newTps);
                // 하향 조절 시에는 락을 걸어 getBucket() 호출을 잠시 차단
                updateBucketConfigurationWithLock();
            }
        }
    }

    protected abstract Bandwidth createBandwidth();

    private Bucket getBucket(String key) {
        if (bucket == null) {
            configurationLock.writeLock().lock();
            try {
                if (bucket == null) {
                    log.debug("버킷 초기 생성. key: {}, 초기 TPS: {}", key, properties.initialTps());

                    Bandwidth newBandwidth = createBandwidth();

                    BucketConfiguration configuration = BucketConfiguration.builder()
                            .addLimit(newBandwidth)
                            .build();

                    bucket = proxyManager.builder().build(key, () -> configuration);
                    lastUpdatedTps.set(properties.initialTps());
                    log.info("버킷 생성 완료. key: {}", key);
                }
            } finally {
                configurationLock.writeLock().unlock();
            }
        } else {
            // 버킷이 이미 존재하는 경우, 하향 조절 중인지 확인
            // 하향 조절은 configurationLock.lock()을 사용하므로 tryLock으로 체크
            if (!configurationLock.readLock().tryLock()) {
                // 락을 획득하지 못했다면 하향 조절 중일 가능성이 높음
                // 잠시 대기 후 다시 시도
                try {
                    Thread.sleep(200); // 짧은 대기
                } catch (InterruptedException e) {
                    throw new RuntimeException("getBucket 대기 중 인터럽트 발생", e);
                }
                return getBucket(key); // 재귀 호출
            } else {
                // 락을 획득했다면 즉시 해제하고 버킷 반환
                configurationLock.readLock().unlock();
            }
        }
        return bucket;
    }

    // 상향 조절용 - 락 없이 비동기 업데이트
    private void updateBucketConfigurationAsync() {
        int currentTpsValue = currentTps.get();

        if (lastUpdatedTps.get() == currentTpsValue) {
            return;
        }

        ReentrantReadWriteLock.WriteLock writeLock = asyncUpdateLock.writeLock();

        if (writeLock.tryLock()) {
            try {
                if (lastUpdatedTps.get() == currentTpsValue) {
                    return;
                }

                log.info("버킷 설정 업데이트 시작 (상향 조정). 이전 TPS: {} -> 새 TPS: {}",
                        lastUpdatedTps.get(), currentTpsValue);

                Bandwidth newBandwidth = createBandwidth();
                BucketConfiguration newConfiguration = BucketConfiguration.builder()
                        .addLimit(newBandwidth)
                        .build();

                if (bucket != null) {
                    bucket.replaceConfiguration(newConfiguration, TokensInheritanceStrategy.ADDITIVE);
                    lastUpdatedTps.set(currentTpsValue);
                    log.info("버킷 설정 업데이트 완료 (상향 조정). TPS: {}", currentTpsValue);
                }

            } catch (Exception e) {
                log.error("버킷 설정 업데이트 실패 (상향 조정). TPS: {}", currentTpsValue, e);
            } finally {
                if(writeLock.isHeldByCurrentThread()) writeLock.unlock();
            }
        } else {
            log.debug("다른 스레드가 버킷 설정 업데이트 중 (상향 조정 스킵). TPS: {}", currentTpsValue);
        }
    }

    // 하향 조절용 - 락을 걸어 getBucket() 호출 차단
    private void updateBucketConfigurationWithLock() {
        int currentTpsValue = currentTps.get();

        if (lastUpdatedTps.get() == currentTpsValue) {
            return;
        }

        ReentrantReadWriteLock.WriteLock writeLock = configurationLock.writeLock();

        writeLock.lock();
        try {
            if (lastUpdatedTps.get() == currentTpsValue) {
                return;
            }

            log.info("버킷 설정 업데이트 시작 (하향 조정). 이전 TPS: {} -> 새 TPS: {}",
                    lastUpdatedTps.get(), currentTpsValue);

            Bandwidth newBandwidth = createBandwidth();
            BucketConfiguration newConfiguration = BucketConfiguration.builder()
                    .addLimit(newBandwidth)
                    .build();

            if (bucket != null) {
                bucket.replaceConfiguration(newConfiguration, TokensInheritanceStrategy.ADDITIVE);
                lastUpdatedTps.set(currentTpsValue);
                log.info("버킷 설정 업데이트 완료 (하향 조정). TPS: {}", currentTpsValue);
            }

        } catch (Exception e) {
            log.error("버킷 설정 업데이트 실패 (하향 조정). TPS: {}", currentTpsValue, e);
        } finally {
            if(writeLock.isHeldByCurrentThread()) writeLock.unlock();
        }
    }
}