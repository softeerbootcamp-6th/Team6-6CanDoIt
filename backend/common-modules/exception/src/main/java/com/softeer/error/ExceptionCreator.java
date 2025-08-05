package com.softeer.error;

public final class ExceptionCreator {

    private ExceptionCreator() {}

    public static CustomException create(ExceptionInterface e) {
        return new CustomException(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }

    public static CustomException create(ExceptionInterface e, String errorLog) {
        return new CustomException(e.getHttpStatus(), e.getErrorCode(), e.getMessage(), e.getErrorCode());
    }
}
