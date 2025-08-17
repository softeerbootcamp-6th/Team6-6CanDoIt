package com.softeer.throttle;

import io.github.bucket4j.*;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
public class ThrottlingManager {

    private final ProxyManager<String> proxyManager;
    private final ThrottlingProperties properties;
    private final BackoffStrategy backoffStrategy;

    private final ScheduledExecutorService retryExecutor = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "throttling-retry-executor");
        t.setDaemon(true);
        return t;
    });

    private final AtomicInteger currentTps = new AtomicInteger();
    private final AtomicInteger successCount = new AtomicInteger();

    private final ReentrantLock configurationLock = new ReentrantLock();
    private final AtomicInteger lastUpdatedTps = new AtomicInteger(-1);

    private volatile boolean shutdown = false;
    private volatile Bucket bucket;

    @PostConstruct
    public void initialize() {
        currentTps.set(properties.initialTps());
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

    /**
     * Submit a task to be executed asynchronously.
     * The task is executed immediately if a token is available.
     * If not, it is added to a retry queue with a backoff strategy.
     *
     * @param key The key to identify the bucket for throttling.
     * @param task The task to be executed, wrapped in a CompletableFuture supplier.
     * @param <T> The type of the result.
     * @return A CompletableFuture that will be completed when the task is done.
     */
    public <T> CompletableFuture<T> submit(String key, Supplier<CompletableFuture<T>> task) {
        if (shutdown) {
            CompletableFuture<T> future = new CompletableFuture<>();
            future.completeExceptionally(new RejectedExecutionException("ThrottlingManager is shutting down"));
            return future;
        }

        CompletableFuture<T> future = new CompletableFuture<>();
        RetryTask<T> retryTask = new RetryTask<>(key, task, future, 0);
        executeOrRetry(retryTask);

        return future;
    }

    private <T> void executeOrRetry(RetryTask<T> task) {
        if (shutdown) {
            task.future().completeExceptionally(new RejectedExecutionException("ThrottlingManager is shutting down"));
            return;
        }

        try {
            Bucket bucket = getBucket(task.key());
            ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

            if (probe.isConsumed()) {
                log.info("토큰 소비 성공. 작업 실행 시작. key: {}, attempt: {}", task.key(), task.attempt());
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

    private <T> void handleTaskException(RetryTask<T> task, Throwable ex) {
        if (ex instanceof ThrottleException throttleException) {
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

    private void scheduleRetry(RetryTask<?> task) {
        if (shutdown) {
            task.future().completeExceptionally(new RejectedExecutionException("ThrottlingManager is shutting down"));
            return;
        }

        long delay = backoffStrategy.computeDelay(task.attempt());
        log.debug("백오프 재시도 예약. key: {}, attempt: {}, delay: {}ms", task.key(), task.attempt(), delay);

        retryExecutor.schedule(() -> executeOrRetry(task), delay, TimeUnit.MILLISECONDS);
    }

    private Bucket getBucket(String key) {
        if (bucket == null) {
            if (configurationLock.tryLock()) {
                try {
                    if (bucket == null) { // Double-check
                        log.debug("버킷 초기 생성. key: {}, 초기 TPS: {}", key, properties.initialTps());

                        Bandwidth initialBandwidth = Bandwidth.builder()
                                .capacity(properties.maxTps())
                                .refillGreedy(properties.initialTps(), Duration.ofSeconds(1))
                                .initialTokens(0)
                                .build();

                        BucketConfiguration configuration = BucketConfiguration.builder()
                                .addLimit(initialBandwidth)
                                .build();

                        bucket = proxyManager.builder().build(key, () -> configuration);
                        lastUpdatedTps.set(properties.initialTps());
                        log.info("버킷 생성 완료. key: {}", key);
                    }
                } finally {
                    configurationLock.unlock();
                }
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("버킷 생성 중 인터럽트 발생", e);
                }
                return getBucket(key); // 재귀 호출
            }
        }
        return bucket;
    }

    private void updateBucketConfiguration() {
        int currentTpsValue = currentTps.get();

        if (lastUpdatedTps.get() == currentTpsValue) {
            return;
        }

        // 락 획득 시도 (non-blocking)
        if (configurationLock.tryLock()) {
            try {
                // Double-check: 락 획득 후 다시 확인
                if (lastUpdatedTps.get() == currentTpsValue) {
                    return;
                }

                log.debug("버킷 설정 업데이트 시작. 이전 TPS: {} -> 새 TPS: {}",
                        lastUpdatedTps.get(), currentTpsValue);

                Bandwidth newBandwidth = Bandwidth.builder()
                        .capacity(properties.maxTps())
                        .refillGreedy(currentTpsValue, Duration.ofSeconds(1))
                        .initialTokens(0)
                        .build();

                BucketConfiguration newConfiguration = BucketConfiguration.builder()
                        .addLimit(newBandwidth)
                        .build();

                if (bucket != null) {
                    bucket.replaceConfiguration(newConfiguration, TokensInheritanceStrategy.ADDITIVE);
                    lastUpdatedTps.set(currentTpsValue);
                    log.info("버킷 설정 업데이트 완료. TPS: {}", currentTpsValue);
                }

            } catch (Exception e) {
                log.error("버킷 설정 업데이트 실패. TPS: {}", currentTpsValue, e);
            } finally {
                configurationLock.unlock();
            }
        } else {
            log.debug("다른 스레드가 버킷 설정 업데이트 중. TPS: {}", currentTpsValue);
        }
    }

    private void adjustTps(boolean success) {
        int oldTps = currentTps.get();
        int newTps;

        if (success) {
            int successCounter = successCount.incrementAndGet();
            if (successCounter >= oldTps && successCounter % oldTps == 0) {
                successCount.set(0);
                newTps = Math.min(properties.maxTps(), oldTps + 1);

                if (currentTps.compareAndSet(oldTps, newTps)) {
                    log.info("TPS 상향 조정: {} -> {} ({}회 연속 성공)", oldTps, newTps, successCounter);
                    updateBucketConfiguration();
                }
            }
        } else {
            successCount.set(0);
            newTps = Math.max(properties.minTps(), oldTps - properties.failStep());

            if (currentTps.compareAndSet(oldTps, newTps)) {
                log.info("TPS 하향 조정: {} -> {} (실패 감지)", oldTps, newTps);
                updateBucketConfiguration();
            }
        }
    }

    private record RetryTask<T>(
            String key,
            Supplier<CompletableFuture<T>> supplier,
            CompletableFuture<T> future,
            int attempt
    ) {
        public RetryTask<T> withIncrementedAttempt() {
            return new RetryTask<>(key, supplier, future, attempt + 1);
        }
    }
}