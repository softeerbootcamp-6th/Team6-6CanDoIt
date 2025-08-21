package com.softeer.throttle.manager;

import com.softeer.throttle.BackoffStrategy;
import com.softeer.throttle.ex.ThrottleException;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
public class SimpleRetryHandler {

    private final BackoffStrategy backoffStrategy;
    private final int maxRetryAttempts;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "retry-handler-scheduler");
        t.setDaemon(true);
        return t;
    });

    private volatile boolean shutdown = false;

    @PreDestroy
    public void shutdown() {
        log.info("RetryHandler 종료 중...");
        shutdown = true;

        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(30, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("RetryHandler 종료 완료");
    }

    /**
     * 작업을 실행하고 실패 시 재시도를 수행합니다.
     *
     * @param key 작업 식별자
     * @param task 실행할 작업
     * @return 작업 실행 결과
     */
    public <T> T submit(String key, Supplier<T> task) {
        return executeWithRetry(key, task, 0);
    }

    private <T> T executeWithRetry(String key, Supplier<T> task, int attempt) {
        if (shutdown) {
            throw new RuntimeException("RetryHandler is shutting down");
        }

        try {
            log.debug("작업 실행. key: {}, attempt: {}", key, attempt);
            return task.get();
        } catch (Exception e) {
            return handleException(key, task, attempt, e);
        }
    }

    private <T> T handleException(String key, Supplier<T> task, int attempt, Exception e) {
        if (e instanceof ThrottleException throttleException && throttleException.isRetryable()) {
            if (attempt < maxRetryAttempts) {
                log.warn("재시도 가능한 예외 발생. key: {}, attempt: {}, ex: {}", key, attempt, e.getMessage());

                return retryAfterDelay(key, task, attempt + 1);
            } else {
                log.error("최대 재시도 횟수 초과. key: {}, maxAttempts: {}", key, maxRetryAttempts, e);
                throw new RuntimeException("Max retry attempts exceeded", e);
            }
        }

        // 재시도 불가능한 예외는 바로 전파
        log.error("재시도 불가능한 예외. key: {}, attempt: {}", key, attempt, e);
        throw new RuntimeException("Non-retryable exception", e);
    }

    private <T> T retryAfterDelay(String key, Supplier<T> task, int nextAttempt) {
        long delay = backoffStrategy.computeDelay(nextAttempt);
        log.debug("재시도 예약. key: {}, nextAttempt: {}, delay: {}ms", key, nextAttempt, delay);

        try {
            CompletableFuture<T> future = new CompletableFuture<>();

            scheduler.schedule(() -> {
                try {
                    T result = executeWithRetry(key, task, nextAttempt);
                    future.complete(result);
                } catch (Exception ex) {
                    future.completeExceptionally(ex);
                }
            }, delay, TimeUnit.MILLISECONDS);

            return future.get(); // 결과를 기다림

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Retry interrupted", e);
        } catch (Exception e) {
            throw new RuntimeException("Retry execution failed", e);
        }
    }
}