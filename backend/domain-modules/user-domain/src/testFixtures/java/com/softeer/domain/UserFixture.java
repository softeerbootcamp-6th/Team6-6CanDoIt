package com.softeer.domain;

import com.softeer.entity.Role;

public class UserFixture {

    public static UserFixtureBuilder builder() {
        return new UserFixtureBuilder();
    }

    public static User createDefault() {
        return builder().build();
    }

    public static class UserFixtureBuilder {
        private long id = 0L;
        private String nickname = "testNickname";
        private String loginId = "testLoginId";
        private String password = "testPassword";
        private Role role = Role.NORMAL;
        private String imageUrl = "testImageUrl";

        public UserFixtureBuilder id(long id) {
            this.id = id;
            return this;
        }

        public UserFixtureBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserFixtureBuilder loginId(String loginId) {
            this.loginId = loginId;
            return this;
        }

        public UserFixtureBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserFixtureBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public UserFixtureBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public User build() {
            return new User(id, nickname, loginId, password, role, imageUrl);
        }
    }
}
