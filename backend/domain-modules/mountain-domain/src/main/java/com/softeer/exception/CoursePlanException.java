package com.softeer.exception;

import com.softeer.error.ExceptionInterface;
import com.softeer.error.HttpStatus;

public enum CoursePlanException implements ExceptionInterface {
    COURSE_PLAN_INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CPN-001", "Course-plan internal error occurred");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    CoursePlanException(HttpStatus httpStatus, String errorCode, String message) {
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
