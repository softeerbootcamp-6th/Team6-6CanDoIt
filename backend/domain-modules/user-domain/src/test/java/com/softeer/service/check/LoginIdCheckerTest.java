package com.softeer.service.check;

import com.softeer.error.CustomException;
import com.softeer.error.ExceptionCreator;
import com.softeer.exception.UserException;
import com.softeer.repository.UserAdapter;
import com.softeer.repository.impl.UserJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginIdCheckerTest {

    private LoginIdChecker loginIdChecker;

    private UserAdapter userAdapter;


    @BeforeEach
    void setUp() {
        userAdapter = mock(UserAdapter.class);
        loginIdChecker = new LoginIdChecker(userAdapter);
    }
    @Test
    @DisplayName("영문과 숫자 포함 6~20자 이내의 유효한 로그인 ID는 true를 반환해야 한다.")
    void validLoginIdShouldReturnTrue() {
        // Given
        String validLoginId = "softeer123";

        // Given - JPA Repository가 존재하지 않는다고 가정
        when(userAdapter.existsByLoginId(validLoginId)).thenReturn(false);

        // When & Then
        Assertions.assertTrue(loginIdChecker.check(validLoginId));
    }

    @Test
    @DisplayName("6자 미만 또는 20자 초과인 로그인 ID는 예외를 발생시켜야 한다.")
    void invalidLoginIdWithInvalidLengthShouldThrowException() {
        // Given
        String tooShort = "a1b2c"; // 5자
        String tooLong = "abcdefg1234567890hijk"; // 21자
        String empty = "";
        String blank = "   ";
        String nullLoginId = null;

        CustomException expected = ExceptionCreator.create(UserException.LOGIN_ID_RANGE_6_20);

        // When & Then
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> loginIdChecker.check(tooShort)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> loginIdChecker.check(tooLong)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> loginIdChecker.check(empty)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> loginIdChecker.check(blank)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> loginIdChecker.check(nullLoginId)));
    }

    @Test
    @DisplayName("영문과 숫자만으로 구성되지 않은 로그인 ID는 예외를 발생시켜야 한다.")
    void invalidLoginIdWithNonEnglishOrDigitShouldThrowException() {
        // Given
        String withSpecialChars = "softeer@123";
        String withKorean = "소프티어123";
        String withSpace = "softeer 123";
        String onlyEnglish = "softeeronly"; // 최소 1개의 숫자 포함 조건 미달
        String onlyDigits = "12345678"; // 최소 1개의 영문 포함 조건 미달

        CustomException expected = ExceptionCreator.create(UserException.LOGIN_ID_ONLY_ENGLISH_AND_DIGIT);

        // When & Then
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> loginIdChecker.check(withSpecialChars)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> loginIdChecker.check(withKorean)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> loginIdChecker.check(withSpace)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> loginIdChecker.check(onlyEnglish)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> loginIdChecker.check(onlyDigits)));
    }

    @Test
    @DisplayName("이미 존재하는 로그인 ID는 예외를 발생시켜야 한다.")
    void duplicatedLoginIdShouldThrowException() {
        // Given
        String duplicatedLoginId = "softeer123";

        when(userAdapter.existsByLoginId(any(String.class))).thenReturn(true);

        CustomException expected = ExceptionCreator.create(UserException.DUPLICATED_LOGIN_ID);

        // When & Then
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> loginIdChecker.check(duplicatedLoginId)));
    }
}