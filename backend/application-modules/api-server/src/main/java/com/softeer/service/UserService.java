package com.softeer.service;

import com.softeer.domain.ImageMeta;
import com.softeer.domain.User;
import com.softeer.dto.request.SignInRequest;
import com.softeer.dto.request.SignUpRequest;
import com.softeer.config.auth.JwtGenerator;
import com.softeer.config.auth.Token;
import com.softeer.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final SignUseCase signUseCase;
    private final JwtGenerator jwtGenerator;
    private final ProfileCommandUseCase profileCommandUseCase;
    private final ImageUseCase imageUseCase;

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

    public void updateNickname(long userId, String nickname) {
        profileCommandUseCase.updateNickname(userId, nickname);
    }

    public void updateImage(long userId, MultipartFile imageFile) {

        long imageId;

        try {
            imageId = imageUseCase.uploadImage(new ImageMeta(imageFile.getBytes(), imageFile.getName(), imageFile.getContentType()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image file", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload image", e);
        }

        profileCommandUseCase.updateProfileImage(userId, imageId);
    }

    public UserProfileResponse getUserProfile(long userId) {
        User user = signUseCase.loadUserByUserId(userId);
        return new UserProfileResponse(user);
    }
}
