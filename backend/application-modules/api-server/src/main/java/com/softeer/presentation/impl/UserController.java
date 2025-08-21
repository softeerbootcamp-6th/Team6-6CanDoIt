package com.softeer.presentation.impl;

import com.softeer.dto.request.SignInRequest;
import com.softeer.dto.request.SignUpRequest;
import com.softeer.dto.request.UpdateNicknameRequest;
import com.softeer.presentation.UserApi;
import com.softeer.config.auth.Token;
import com.softeer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<Void> checkNickname(String nickname) {
        userService.existsNickname(nickname);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> checkLoginId(String loginId) {
        userService.existsLoginId(loginId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> signUp(SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Token> signIn(SignInRequest signInRequest) {
        Token token = userService.signIn(signInRequest);

        return ResponseEntity.ok(token);
    }

    @Override
    public ResponseEntity<Void> updateNickname(Long userId, UpdateNicknameRequest updateNicknameRequest) {
        userService.updateNickname(userId, updateNicknameRequest.nickname());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateNickname(Long userId, MultipartFile imageFile) {
        userService.updateImage(userId, imageFile);

        return ResponseEntity.ok().build();
    }
}
