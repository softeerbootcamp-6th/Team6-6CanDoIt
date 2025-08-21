package com.softeer.service;

public interface ProfileCommandUseCase {
    public void updateNickname(long userId, String nickname);
    public void updateProfileImage(long userId, long imageId);
}
