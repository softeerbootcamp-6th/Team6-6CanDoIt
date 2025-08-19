package com.softeer.presentation.impl;

import com.softeer.dto.request.SignInRequest;
import com.softeer.dto.request.SignUpRequest;
import com.softeer.presentation.SignApi;
import com.softeer.config.auth.Token;
import com.softeer.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignController implements SignApi {

    private final SignService service;

    @Override
    public ResponseEntity<Void> checkNickname(String nickname) {
        service.existsNickname(nickname);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> checkLoginId(String loginId) {
        service.existsLoginId(loginId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> signUp(SignUpRequest signUpRequest) {
        service.signUp(signUpRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Token> signIn(SignInRequest signInRequest) {
        Token token = service.signIn(signInRequest);

        return ResponseEntity.ok(token);
    }
}
