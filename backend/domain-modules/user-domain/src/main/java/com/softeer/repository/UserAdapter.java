package com.softeer.repository;

public interface UserAdapter {
    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname);
    void save(String loginId, String nickname, String password);
    boolean existsByLoginIdAndPassword(String loginId, String password);
}
