package com.softeer.service;

public interface ProfileCommandUseCase {
    void updateNickname(long userId, String nickname);
    void updateProfileImage(long userId, long imageId);
}
