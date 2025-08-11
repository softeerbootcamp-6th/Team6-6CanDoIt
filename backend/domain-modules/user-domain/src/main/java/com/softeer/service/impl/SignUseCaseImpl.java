package com.softeer.service.impl;

import com.softeer.error.ExceptionCreator;
import com.softeer.exception.UserException;
import com.softeer.repository.UserAdapter;
import com.softeer.service.SignUseCase;
import com.softeer.service.check.LoginIdChecker;
import com.softeer.service.check.NickNameChecker;
import com.softeer.service.check.PasswordChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUseCaseImpl implements SignUseCase {

    private final NickNameChecker nickNameChecker;
    private final LoginIdChecker loginIdChecker;
    private final PasswordChecker passwordChecker;
    private final UserAdapter userAdapter;

    @Override
    public void checkLoginId(String loginId) {
        loginIdChecker.check(loginId);
    }

    @Override
    public void checkNickname(String nickname) {
        nickNameChecker.check(nickname);
    }

    @Override
    public void singUp(SingUpRequest singUpRequest) {
        String nickname = singUpRequest.nickname();
        String loginId = singUpRequest.loginId();
        String password = singUpRequest.password();

        validateSignUpRequest(nickname, loginId, password);
        userAdapter.save(loginId, nickname, password);
    }

    @Override
    public void signIn(SignInRequest signInRequest) {
        if(userAdapter.existsByLoginIdAndPassword(signInRequest.loginId(), signInRequest.password()))
            throw ExceptionCreator.create(UserException.WRONG_LOGIN_ID_OR_PASSWORD, "loginId : " + signInRequest.loginId() + " password : " + signInRequest.password());
    }

    private void validateSignUpRequest(String nickname, String loginId, String password) {
        nickNameChecker.check(nickname);
        loginIdChecker.check(loginId);
        passwordChecker.check(password);
    }
}
