package com.softeer.throttle.task;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public record RetryTaskWithBucket<T>(
        String key, Supplier<CompletableFuture<T>> supplier,
        CompletableFuture<T> future, int attempt) {

    public RetryTaskWithBucket<T> withIncrementedAttempt() {
        return new RetryTaskWithBucket<>(key, supplier, future, attempt + 1);
    }
}
