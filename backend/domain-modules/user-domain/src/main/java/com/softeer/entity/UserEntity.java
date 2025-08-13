package com.softeer.entity;

import com.softeer.domain.Image;
import com.softeer.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Table(name = "user")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private ImageEntity imageEntity;

    public static User toDomain(UserEntity userEntity) {
        return new User(userEntity.id, userEntity.nickname, userEntity.loginId, userEntity.password,
                userEntity.role, Objects.isNull(userEntity.imageEntity) ? null : userEntity.imageEntity.getImageUrl());
    }

    public static UserEntity from(User user, Image image) {
        return new UserEntity(user.id(), user.nickname(), user.loginId(), user.password(), user.role(), ImageEntity.from(image));
    }

    public static UserEntity from(String nickname, String loginId, String password) {
        return UserEntity.builder().nickname(nickname).loginId(loginId).password(password).role(Role.NORMAL).build();
    }
}
