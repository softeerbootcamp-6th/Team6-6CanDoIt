package com.softeer.exception;

import com.softeer.error.ExceptionInterface;
import com.softeer.error.HttpStatus;

public enum MountainException implements ExceptionInterface {
    NOT_FOUND(HttpStatus.NOT_FOUND, "MTN-001", "Mountain not found");

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;

    MountainException(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
