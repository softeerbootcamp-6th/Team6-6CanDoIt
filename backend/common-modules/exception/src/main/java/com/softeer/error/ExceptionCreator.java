package com.softeer.error;

public final class ExceptionCreator {

    private ExceptionCreator() {}

    public static CustomException create(ExceptionInterface e) {
        return new CustomException(e.getMessage(), e.getHttpStatus(), e.getErrorCode());
    }

    public static CustomException create(ExceptionInterface e, String errorLog) {
        return new CustomException(e.getMessage(), e.getHttpStatus(), e.getErrorCode(), errorLog);
    }
}
