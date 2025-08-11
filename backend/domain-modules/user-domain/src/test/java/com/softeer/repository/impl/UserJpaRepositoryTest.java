package com.softeer.repository.impl;

import com.softeer.SpringBootTestWithContainer;
import com.softeer.domain.ImageFixture;
import com.softeer.domain.User;
import com.softeer.domain.UserFixture;
import com.softeer.entity.ImageEntity;
import com.softeer.entity.UserEntity;
import com.softeer.repository.ImageJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTestWithContainer
class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private ImageJpaRepository imageJpaRepository;

    @BeforeEach
    void setUp() {
        userJpaRepository.deleteAll();
    }

    @Test
    public void existsByNickname_return_true() {
        //given
        ImageEntity imageEntity = imageJpaRepository.saveAndFlush(ImageEntity.from(ImageFixture.createDefault()));

        User user = UserFixture.builder().build();
        userJpaRepository.saveAndFlush(UserEntity.from(user, ImageEntity.toDomain(imageEntity)));

        //when & then
        Assertions.assertTrue(userJpaRepository.existsByNickname(user.nickname()));
    }

    @Test
    public void existsByNickname_return_false() {
        //given
        User user = UserFixture.builder().build();

        //when & then
        Assertions.assertFalse(userJpaRepository.existsByNickname(user.nickname()));
    }

    @Test
    public void existsByLoginId_return_true() {
        //given
        ImageEntity imageEntity = imageJpaRepository.saveAndFlush(ImageEntity.from(ImageFixture.createDefault()));

        User user = UserFixture.builder().build();
        userJpaRepository.saveAndFlush(UserEntity.from(user, ImageEntity.toDomain(imageEntity)));

        //when & then
        Assertions.assertTrue(userJpaRepository.existsByLoginId(user.loginId()));
    }

    @Test
    public void existsByLoginId_return_false() {
        //given
        User user = UserFixture.builder().build();

        //when & then
        Assertions.assertFalse(userJpaRepository.existsByLoginId(user.loginId()));
    }

    @Test
    public void save() {
        User user = UserFixture.createDefault();

        UserEntity userEntity = UserEntity.from(user.nickname(), user.loginId(), user.password());
        userJpaRepository.saveAndFlush(userEntity);
    }
}