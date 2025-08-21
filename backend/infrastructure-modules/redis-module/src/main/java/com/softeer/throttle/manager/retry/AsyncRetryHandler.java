package com.softeer.throttle.manager.retry;

import com.softeer.throttle.BackoffStrategy;
import com.softeer.throttle.ex.ThrottleException;
import com.softeer.throttle.task.RetryTask;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
public class AsyncRetryHandler {

    private final BackoffStrategy backoffStrategy;
    private final int timeOutSeconds;

    private final ScheduledExecutorService retryExecutor = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "throttling-retry-executor");
        t.setDaemon(true);
        return t;
    });

    private volatile boolean shutdown = false;

    @PreDestroy
    public void shutdown() {
        log.info("ThrottlingManager 종료 중...");
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
        log.info("ThrottlingManager 종료 완료");
    }

    /**
     * 비동기 방식으로 작업을 제출하고 CompletableFuture를 반환합니다.
     *
     * @param key 작업 식별자
     * @param task 실행할 작업
     * @param executor 사용할 ExecutorService (null이면 기본 executor 사용)
     * @return 작업 실행 결과를 담은 CompletableFuture
     */
    public <T> CompletableFuture<T> submitAsync(String key, Supplier<T> task, ExecutorService executor) {
        if (shutdown) {
            CompletableFuture<T> future = new CompletableFuture<>();
            future.completeExceptionally(new RejectedExecutionException("ThrottlingManager is shutting down"));
            return future;
        }

        CompletableFuture<T> future = new CompletableFuture<>();
        RetryTask<T> retryTask = new RetryTask<>(key, task, future, 0);
        executeOrRetry(retryTask, executor);

        return future.orTimeout(timeOutSeconds, TimeUnit.SECONDS)
                .handle((result, throwable) -> {
                    if (throwable instanceof TimeoutException) {
                        log.error("작업 타임아웃. key: {}, timeOutSeconds: {}", key, timeOutSeconds);
                        throw new RuntimeException("Task timed out after " + timeOutSeconds + " seconds", throwable);
                    } else if (throwable != null) {
                        throw new RuntimeException(throwable);
                    }
                    return result;
                });
    }

    private <T> void executeOrRetry(RetryTask<T> task, ExecutorService executor) {
        if (shutdown) {
            task.future().completeExceptionally(new RejectedExecutionException("ThrottlingManager is shutting down"));
            return;
        }

        try {
            CompletableFuture.supplyAsync(() -> task.supplier().get(), executor)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
                            handleTaskException(task, cause, executor);
                        } else {
                            task.future().complete(result);
                        }
                    });

        } catch (Exception e) {
            log.error("executeOrRetry에서 예외 발생. key: {}", task.key(), e);
            task.future().completeExceptionally(e);
        }
    }

    private <T> void handleTaskException(RetryTask<T> task, Throwable ex, ExecutorService executor) {
        if (ex instanceof ThrottleException throttleException) {
            if (throttleException.isRetryable()) {
                log.info("재시도 가능한 예외 발생. key: {}, attempt: {}, ex: {}", task.key(), task.attempt(), ex.getMessage());
                scheduleRetry(task.withIncrementedAttempt(), executor);
            } else {
                log.error("재시도 불가능한 예외. key: {}, attempt: {}", task.key(), task.attempt(), ex);
                task.future().completeExceptionally(ex);
            }
        } else {
            log.error("작업 실행 중 예외 발생. key: {}, attempt: {}", task.key(), task.attempt(), ex);
            task.future().completeExceptionally(ex);
        }
    }

    private void scheduleRetry(RetryTask<?> task, ExecutorService executor) {
        if (shutdown) {
            task.future().completeExceptionally(new RejectedExecutionException("ThrottlingManager is shutting down"));
            return;
        }

        long delay = backoffStrategy.computeDelay(task.attempt());
        log.debug("백오프 재시도 예약. key: {}, attempt: {}, delay: {}ms", task.key(), task.attempt(), delay);

        retryExecutor.schedule(() -> executeOrRetry(task, executor), delay, TimeUnit.MILLISECONDS);
    }
}