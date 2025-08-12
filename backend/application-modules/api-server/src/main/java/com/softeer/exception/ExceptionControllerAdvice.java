package com.softeer.exception;

import com.softeer.error.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException e) {
        log.error("[" + e.getErrorCode() + "] : " + (Objects.isNull(e.getErrorLog()) ? ""  : e.getErrorLog()));
        return ExceptionResponse.toResponseEntity(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleInternalException(Exception e) {
        log.error(e.getMessage());
        return ExceptionResponse.toResponseEntity(e);
    }
}
