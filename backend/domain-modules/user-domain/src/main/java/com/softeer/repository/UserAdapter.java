package com.softeer.repository;

import com.softeer.entity.UserEntity;

import java.util.Optional;

public interface UserAdapter {
    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname);
    void save(String loginId, String nickname, String password);
    Optional<UserEntity> findByLoginIdAndPassword(String loginId, String password);
}
