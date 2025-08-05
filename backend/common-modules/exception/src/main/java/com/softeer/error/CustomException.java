package com.softeer.error;

public class CustomException extends RuntimeException {

    private final int status;
    private final String errorCode;
    private final String message;
    private final String errorLog;

    public CustomException(int status, String errorCode, String message) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.errorLog = null;
    }

    public CustomException(int status, String errorCode, String message, String errorLog) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.errorLog = errorLog;
    }
}
