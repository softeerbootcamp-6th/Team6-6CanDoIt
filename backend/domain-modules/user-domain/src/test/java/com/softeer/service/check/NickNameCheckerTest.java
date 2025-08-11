package com.softeer.service.check;

import com.softeer.error.CustomException;
import com.softeer.error.ExceptionCreator;
import com.softeer.exception.UserException;
import com.softeer.repository.UserAdapter;
import com.softeer.service.Check;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NickNameCheckerTest {

    private Check nickNameChecker;
    private UserAdapter userAdapter;

    @BeforeEach
    void setUp() {
        userAdapter = mock(UserAdapter.class);
        nickNameChecker = new NickNameChecker(userAdapter);
    }

    @Test
    @DisplayName("2자 이상 20자 이하의 올바른 한글 닉네임은 유효해야 한다.")
    void validKoreanNicknameShouldBeTrue() {
        // Given
        String validNicknameMinLength = "가나가나가나";
        String validNicknameMaxLength = "가나다라마바사아자차카타파하가나다라";

        when(userAdapter.existsByNickname(any(String.class))).thenReturn(false);

        // When & Then
        assertTrue(nickNameChecker.check(validNicknameMinLength));
        assertTrue(nickNameChecker.check(validNicknameMaxLength));
    }

    @Test
    @DisplayName("한글 외 다른 문자가 포함된 닉네임은 유효하지 않아야 한다.")
    void invalidNicknameWithNonKoreanCharactersShouldBeFalse() {
        // Given
        String withNumbers = "홍길동123";
        String withEnglish = "홍길동abc";
        String withSpecialChars = "홍길동!@#";
        String withMixedChars = "홍1길a동!";

        CustomException expected = ExceptionCreator.create(UserException.NICKNAME_ONLY_KOREAN);
        // When & Then
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> nickNameChecker.check(withNumbers)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> nickNameChecker.check(withEnglish)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> nickNameChecker.check(withSpecialChars)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> nickNameChecker.check(withMixedChars)));
    }

    @Test
    @DisplayName("2자 미만, 20자 초과의 닉네임은 유효하지 않아야 한다.")
    void invalidNicknameWithInvalidLengthShouldBeFalse() {
        // Given
        String tooShort = "가";
        String tooLong = "가라가나다라마바사아자차카타파하가나다마라"; // 21자
        String emptyString = "";
        String blankString = "   ";
        String nullNickname = null;

        CustomException expected = ExceptionCreator.create(UserException.NICKNAME_RANGE_2_20);

        // When & Then
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> nickNameChecker.check(tooShort)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> nickNameChecker.check(tooLong)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> nickNameChecker.check(emptyString)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> nickNameChecker.check(blankString)));
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> nickNameChecker.check(nullNickname)));
    }

    @Test
    @DisplayName("이미 중복된 닉네임이 존재합니다.")
    void invalidNicknameWithAlreadyExist() {
        // Given
        String validNickname = "가나가나가나";

        when(userAdapter.existsByNickname(any(String.class))).thenReturn(true);
        CustomException expected = ExceptionCreator.create(UserException.DUPLICATED_NICKNAME);

        // When & Then
        Assertions.assertEquals(expected, Assertions.assertThrowsExactly(CustomException.class, () -> nickNameChecker.check(validNickname)));
    }
}