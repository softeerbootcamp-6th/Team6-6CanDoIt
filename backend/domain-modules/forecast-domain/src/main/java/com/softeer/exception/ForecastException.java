package com.softeer.exception;

import com.softeer.error.ExceptionInterface;
import com.softeer.error.HttpStatus;

public enum ForecastException implements ExceptionInterface {
    NOT_FOUND(HttpStatus.NOT_FOUND, "FCT-001", "존재하지 않는 예보입니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    ForecastException(HttpStatus httpStatus, String errorCode, String message) {
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
