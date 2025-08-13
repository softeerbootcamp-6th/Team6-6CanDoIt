package com.softeer.service;

import com.softeer.domain.User;
import com.softeer.dto.request.SignInRequest;
import com.softeer.dto.request.SignUpRequest;
import com.softeer.security.auth.JwtGenerator;
import com.softeer.security.auth.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignService {
    private final SignUseCase signUseCase;
    private final JwtGenerator jwtGenerator;

    public void existsLoginId(String loginId) {
        signUseCase.checkLoginId(loginId);
    }

    public void existsNickname(String nickname) {
        signUseCase.checkNickname(nickname);
    }

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        SignUseCase.SignUpCommand signUpCommand = new SignUseCase.SignUpCommand(signUpRequest.nickname(), signUpRequest.loginId(), signUpRequest.password());
        signUseCase.singUp(signUpCommand);
    }

    public Token signIn(SignInRequest signInRequest) {
        SignUseCase.SignInCommand signInCommand = new SignUseCase.SignInCommand(signInRequest.loginId(), signInRequest.password());
        User user = signUseCase.signIn(signInCommand);

        return jwtGenerator.createAccessToken(user.id(), user.role());
    }
}
