package com.softeer.service;

import com.softeer.domain.User;

public interface SignUseCase {
    void checkLoginId(String loginId);

    void checkNickname(String nickname);

    void singUp(SignUpCommand signUpCommand);

    record SignUpCommand(String nickname, String loginId, String password) {}

    User signIn(SignInCommand signInCommand);

    record SignInCommand(String loginId, String password) {}

    User loadUserByUserId(long userId);
}
