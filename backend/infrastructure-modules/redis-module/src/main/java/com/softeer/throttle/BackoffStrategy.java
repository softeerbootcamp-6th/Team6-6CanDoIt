package com.softeer.throttle;

public class BackoffStrategy {
    private final long baseDelay;
    private final long maxDelay;

    public BackoffStrategy(long baseDelay, long maxDelay) {
        this.baseDelay = baseDelay;
        this.maxDelay = maxDelay;
    }

    public long computeDelay(int attempt) {
        long delay = (long) (baseDelay * Math.pow(2, attempt));
        return Math.min(delay, maxDelay);
    }
}
