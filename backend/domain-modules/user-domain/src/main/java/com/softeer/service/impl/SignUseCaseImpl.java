package com.softeer.service.impl;

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
        nickNameChecker.check(singUpRequest.nickname());
        loginIdChecker.check(singUpRequest.loginId());
        passwordChecker.check(singUpRequest.password());

        userAdapter.save(singUpRequest.loginId(), singUpRequest.nickname(), singUpRequest.password());
    }
}
