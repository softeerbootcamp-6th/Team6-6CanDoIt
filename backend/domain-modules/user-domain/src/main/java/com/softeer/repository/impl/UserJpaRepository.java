package com.softeer.repository.impl;

import com.softeer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    
    boolean existsByLoginId(@Param("loginId") String loginId);
    boolean existsByNickname(@Param("nickname") String nickname);

    @Query("select u from UserEntity u where u.loginId = :loginId and u.password = :password")
    Optional<UserEntity> findByLoginIdAndPassword(@Param("loginId") String loginId, @Param("password") String password);


    @Modifying
    @Query("update UserEntity u set u.nickname = :nickname where u.id = :userId")
    void updateNickname(@Param("userId") long userId, @Param("nickname") String nickname);

    @Modifying
    @Query("update UserEntity u set u.imageEntity.id = :imageId where u.id = :userId")
    void updateImage(@Param("userId") long userId, @Param("imageId") long imageId);

    @Modifying
    @Query(value = "INSERT INTO USER (nickname, login_id, password, image_id) VALUES (:nickname, :loginId, :password, :imageId)", nativeQuery = true)
    void saveUser(@Param("nickname") String nickname, @Param("loginId") String loginId, @Param("password") String password, @Param("imageId") Long imageId);
}
