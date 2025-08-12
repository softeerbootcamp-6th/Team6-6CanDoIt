package com.softeer.exception;

import com.softeer.error.CustomException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ExceptionResponse {
    private final String errorCode;
    private final String message;
    private final String timeStamp = LocalDateTime.now().toString();

    public static ResponseEntity<ExceptionResponse> toResponseEntity(CustomException e) {
        return ResponseEntity
                .status(e.getStatus().getCode())
                .body(
                        ExceptionResponse.builder()
                                .errorCode(e.getErrorCode())
                                .message(e.getMessage())
                                .build()
                );
    }

    public static ResponseEntity<ExceptionResponse> toResponseEntity(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .errorCode("INTERNAL_SERVER_ERROR")
                                .message(e.getMessage())
                                .build()
                );
    }
}
