package com.softeer.error;

import java.util.Objects;

public class CustomException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;
    private final String errorLog;

    public CustomException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.errorLog = null;
    }

    public CustomException(String message, HttpStatus status, String errorCode, String errorLog) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.errorLog = errorLog;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorLog() {
        return errorLog;
    }

    @Override
    public String toString() {
        String logPrefix = "[" + this.errorCode + "]";
        return Objects.isNull(errorLog) ? logPrefix : logPrefix + ": " + errorLog;
    }
}
