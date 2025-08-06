package com.softeer.error;

public interface ExceptionInterface {
    HttpStatus getHttpStatus();
    String getErrorCode();
    String getMessage();
}
