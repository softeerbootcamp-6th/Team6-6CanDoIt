package com.softeer.exception;

import com.softeer.error.ExceptionInterface;
import com.softeer.error.HttpStatus;

public enum ReportException implements ExceptionInterface {
    NO_REPORT_TYPE(HttpStatus.BAD_REQUEST, "RPT-001", "제보 타입을 선택해주세요.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    ReportException(HttpStatus httpStatus, String errorCode, String message) {
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
