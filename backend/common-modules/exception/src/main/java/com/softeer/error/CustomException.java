package com.softeer.error;

public class CustomException extends RuntimeException {

    private final int status;
    private final String errorCode;
    private final String errorLog;

    public CustomException(String message, int status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.errorLog = null;
    }

    public CustomException(String message, int status, String errorCode, String errorLog) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.errorLog = errorLog;
    }

    public int getStatus() {
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
