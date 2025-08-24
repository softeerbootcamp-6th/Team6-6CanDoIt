package com.softeer.repository.impl;

import com.softeer.entity.UserEntity;
import com.softeer.repository.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserAdapterImpl implements UserAdapter {

    private final UserJpaRepository userJpaRepository;

    @Override
    public boolean existsByLoginId(String loginId) {
        return userJpaRepository.existsByLoginId(loginId);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }

    @Override
    public void save(String nickname, String loginId, String password) {
        userJpaRepository.save(UserEntity.from(nickname, loginId, password));
    }

    @Override
    public Optional<UserEntity> findByLoginIdAndPassword(String loginId, String password) {
        return userJpaRepository.findByLoginIdAndPassword(loginId, password);
    }

    @Override
    public void updateNickname(long userId, String nickname) {
        userJpaRepository.updateNickname(userId, nickname);
    }

    @Override
    public void updateImage(long userId, long imageId) {
        userJpaRepository.updateImage(userId, imageId);
    }

    @Override
    public Optional<UserEntity> loadUserByUserId(long userId) {
        return userJpaRepository.findById(userId);
    }
}
