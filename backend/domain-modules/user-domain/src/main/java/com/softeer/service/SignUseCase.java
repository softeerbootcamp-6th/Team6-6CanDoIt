package com.softeer.service;

public interface SignUseCase {
    void checkLoginId(String loginId);
    void checkNickname(String nickname);

    void singUp(SingUpRequest singUpRequest);

    record SingUpRequest(String nickname, String loginId, String password) { }

    void signIn(SignInRequest signInRequest);

    record SignInRequest(String loginId, String password) { }
}
