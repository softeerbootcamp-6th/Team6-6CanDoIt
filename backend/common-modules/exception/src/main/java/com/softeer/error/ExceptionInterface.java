package com.softeer.error;

public interface ExceptionInterface {
    int getHttpStatus();
    String getErrorCode();
    String getMessage();
}
