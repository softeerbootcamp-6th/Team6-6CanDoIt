package com.softeer.config;

import com.softeer.error.ExceptionInterface;
import com.softeer.error.HttpStatus;

public enum JwtException implements ExceptionInterface {

    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "JWT-001", "로그인이 필요한 서비스입니다."),
    FORBIDDEN_USER(HttpStatus.FORBIDDEN, "JWT-002", "권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    JwtException(HttpStatus httpStatus, String errorCode, String message) {
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
