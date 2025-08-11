package com.softeer.service.check;

import com.softeer.error.CustomException;
import com.softeer.error.ExceptionCreator;
import com.softeer.exception.UserException;
import com.softeer.service.Check;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PasswordCheckerTest {

    private Check passwordChecker;

    @BeforeEach
    void setUp() {
        passwordChecker = new PasswordChecker();
    }

    @Test
    @DisplayName("영문, 숫자 포함 8자 이상의 올바른 비밀번호는 유효해야 한다.")
    void validPasswordShouldBeTrue() {
        // Given
        String validPassword = "password123";
        String validPasswordMixedCase = "Password123";
        String validPasswordMinLength = "a1b2c3d4";

        // When & Then
        Assertions.assertTrue(passwordChecker.check(validPassword));
        Assertions.assertTrue(passwordChecker.check(validPasswordMixedCase));
        Assertions.assertTrue(passwordChecker.check(validPasswordMinLength));
    }

    @Test
    @DisplayName("8자 미만의 비밀번호는 유효하지 않아야 한다.")
    void invalidPasswordWithInvalidLengthShouldThrowException() {
        // Given
        String tooShort = "a1b2c3d"; // 7자
        String tooShort2 = "1234567"; // 7자
        String emptyString = "";
        String blankString = "   ";
        String nullPassword = null;

        CustomException expected = ExceptionCreator.create(UserException.PASSWORD_OVER_8);

        // When & Then
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> passwordChecker.check(tooShort)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> passwordChecker.check(tooShort2)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> passwordChecker.check(emptyString)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> passwordChecker.check(blankString)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> passwordChecker.check(nullPassword)));
    }

    @Test
    @DisplayName("영문 또는 숫자가 포함되지 않은 비밀번호는 유효하지 않아야 한다.")
    void invalidPasswordWithMissingCharactersShouldThrowException() {
        // Given
        String onlyEnglish = "passwordonly";
        String onlyDigits = "123456789";
        String withSpecialChars = "12345678!";
        String koreanWithDigits = "비밀번호1234";

        CustomException expected = ExceptionCreator.create(UserException.PASSWORD_ONLY_ENGLISH_AND_DIGIT);

        // When & Then
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> passwordChecker.check(onlyEnglish)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> passwordChecker.check(onlyDigits)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> passwordChecker.check(withSpecialChars)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> passwordChecker.check(koreanWithDigits)));
    }
}