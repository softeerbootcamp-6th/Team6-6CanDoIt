package com.softeer.service.impl;

import com.softeer.domain.User;
import com.softeer.entity.UserEntity;
import com.softeer.error.ExceptionCreator;
import com.softeer.exception.UserException;
import com.softeer.repository.UserAdapter;
import com.softeer.service.SignUseCase;
import com.softeer.service.check.LoginIdChecker;
import com.softeer.service.check.NickNameChecker;
import com.softeer.service.check.PasswordChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
    public User signIn(SignInCommand signInCommand) {
        return userAdapter.findByLoginIdAndPassword(signInCommand.loginId(), signInCommand.password())
                .map(UserEntity::toDomain)
                .orElseThrow(() -> ExceptionCreator.create(UserException.WRONG_LOGIN_ID_OR_PASSWORD, "loginId : " + signInCommand.loginId() + " password : " + signInCommand.password()));
    }

    private void validateSignUpRequest(String nickname, String loginId, String password) {
        nickNameChecker.check(nickname);
        loginIdChecker.check(loginId);
        passwordChecker.check(password);
    }
}
