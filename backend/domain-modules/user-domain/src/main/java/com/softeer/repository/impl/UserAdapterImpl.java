package com.softeer.repository.impl;

import com.softeer.entity.UserEntity;
import com.softeer.repository.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public void save(String loginId, String nickname, String password) {
        userJpaRepository.save(UserEntity.from(loginId, nickname, password));
    }

    @Override
    public boolean existsByLoginIdAndPassword(String loginId, String password) {
        return userJpaRepository.existsByLoginIdAndPassword(loginId, password);
    }
}
