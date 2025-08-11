package com.softeer.repository.impl;

import com.softeer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    @Query("select u from UserEntity u  inner join fetch ImageEntity i on u.imageEntity.id = i.id where u.id = :id")
    Optional<UserEntity> findById(@Param("id") long id);
    boolean existsByLoginId(@Param("loginId") String loginId);
    boolean existsByNickname(@Param("nickname") String nickname);
    boolean existsByLoginIdAndPassword(@Param("loginId") String loginId, @Param("password") String password);
}
