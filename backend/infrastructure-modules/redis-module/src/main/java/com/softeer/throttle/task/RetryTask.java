package com.softeer.throttle.task;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public record RetryTask<T>(
        String key, Supplier<T> supplier,
        CompletableFuture<T> future, int attempt) {

    public RetryTask<T> withIncrementedAttempt() {return new RetryTask<>(key, supplier, future, attempt + 1);}
}

