package com.softeer.throttle;

import lombok.Getter;

@Getter
public class ThrottleException extends RuntimeException {

    private final ThrottleExceptionStatus status;

    public ThrottleException(ThrottleExceptionStatus status) {
        this.status = status;
    }

    public boolean isRetryable() {
        return status == ThrottleExceptionStatus.RETRY;
    }
}
