package com.softeer.service.impl;

import com.softeer.repository.UserAdapter;
import com.softeer.service.ProfileCommandUseCase;
import com.softeer.service.check.NickNameChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileCommandUseCaseImpl implements ProfileCommandUseCase {

    private final NickNameChecker nickNameChecker;
    private final UserAdapter userAdapter;


    @Override
    @Transactional
    public void updateNickname(long userId, String nickname) {
        nickNameChecker.check(nickname);
        userAdapter.updateNickname(userId, nickname);
    }

    @Override
    @Transactional
    public void updateProfileImage(long userId, long imageId) {
        userAdapter.updateImage(userId, imageId);
    }
}
