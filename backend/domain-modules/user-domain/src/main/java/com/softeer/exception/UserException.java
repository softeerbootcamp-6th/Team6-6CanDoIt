package com.softeer.exception;

import com.softeer.error.ExceptionInterface;
import com.softeer.error.HttpStatus;

public enum UserException implements ExceptionInterface {

    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST, "USR-001", "이미 등록된 닉네임입니다."),
    NICKNAME_ONLY_KOREAN(HttpStatus.BAD_REQUEST, "USR-002", "한글로 된 닉네임만 가능합니다. 다시 한번 확인해주세요."),
    NICKNAME_RANGE_2_20(HttpStatus.BAD_REQUEST, "USR-003", "닉네임은 2자 이상 20자 이하여야 합니다."),

    PASSWORD_OVER_8(HttpStatus.BAD_REQUEST, "USR-004", "비밀번호는 8자 이상이여야 합니다."),
    PASSWORD_ONLY_ENGLISH_AND_DIGIT(HttpStatus.BAD_REQUEST, "USR-005", "영어와 숫자로 된 비밀번호만 가능합니다. 다시 한번 확인해주세요."),

    DUPLICATED_LOGIN_ID(HttpStatus.BAD_REQUEST, "USR-006", "이미 등록된 아이디입니다."),
    LOGIN_ID_RANGE_6_20(HttpStatus.BAD_REQUEST, "USR-007", "아이디는 6자 이상 20자 이하여야 합니다."),
    LOGIN_ID_ONLY_ENGLISH_AND_DIGIT(HttpStatus.BAD_REQUEST, "USR-008", "영어와 숫자로 된 아이디만 가능합니다. 다시 한번 확인해주세요."),

    DUPLICATED_NICKNAME_OR_LOGIN_ID(HttpStatus.BAD_REQUEST, "USER-009", "이미 등록된 닉네임 또는 아이디입니다."),

    WRONG_LOGIN_ID_OR_PASSWORD(HttpStatus.BAD_REQUEST, "USR-010", "아이디 또는 패스워드를 잘못 입력하셨습니다."),

    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "USR-011", "유저가 존재하지 않습니다.")


    ;
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    UserException(HttpStatus httpStatus, String errorCode, String message) {
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
