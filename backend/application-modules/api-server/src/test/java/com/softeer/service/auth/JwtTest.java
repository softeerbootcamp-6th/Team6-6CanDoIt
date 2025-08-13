package com.softeer.service.auth;

import com.softeer.security.auth.JwtGenerator;
import com.softeer.security.auth.JwtProperties;
import com.softeer.security.auth.JwtResolver;
import com.softeer.security.auth.Token;
import com.softeer.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class JwtTest {

    private JwtGenerator jwtGenerator;
    private JwtResolver jwtResolver;

    private static final long EXPIRATION = 1000 * 60 * 15; // 15분
    private static final String SECRET_KEY = "super-secret-key-for-jwt-which-should-be-very-long";

    @BeforeEach
    void setUp() {
        JwtProperties properties = new JwtProperties(EXPIRATION, SECRET_KEY);

        jwtGenerator = new JwtGenerator(properties);
        jwtResolver = new JwtResolver(properties);
        jwtGenerator.init();
        jwtResolver.init();
    }

    @Test
    void validate_return_true() {
        // given
        Token token = jwtGenerator.createAccessToken(999L, Role.MANAGER);

        // when & then
        assertThat(jwtResolver.validate(token.value())).isTrue();
    }

    @Test
    void validate_return_false_with_incorrect_expiry() throws InterruptedException {
        // given
        JwtProperties properties = new JwtProperties(1L, SECRET_KEY);
        jwtGenerator = new JwtGenerator(properties);
        jwtGenerator.init();

        Thread.sleep(1L);

        Token token = jwtGenerator.createAccessToken(999L, Role.MANAGER);

        // when & then
        assertThat(jwtResolver.validate(token.value())).isFalse();
    }

    @Test
    void jwt_ROLE_NORMAL_TEST() {
        // given
        long userId = 42L;
        Role role = Role.NORMAL;

        // when
        Token token = jwtGenerator.createAccessToken(userId, role);

        // then
        Role reslovedRole = jwtResolver.getRole(token.value());
        assertThat(reslovedRole).isEqualTo(role);
    }

    @Test
    void jwt_ROLE_MANAGER_TEST() {
        // given
        long userId = 42L;
        Role role = Role.MANAGER;

        // when
        Token token = jwtGenerator.createAccessToken(userId, role);

        // then
        Role reslovedRole = jwtResolver.getRole(token.value());
        assertThat(reslovedRole).isEqualTo(role);
    }

    @Test
    void getUserId_shouldReturnCorrectUserId() {
        // given
        long userId = 123L;

        Token token = jwtGenerator.createAccessToken(userId, Role.MANAGER);

        // when
        long resolvedUserId = jwtResolver.getUserId(token.value());

        // then
        assertThat(resolvedUserId).isEqualTo(resolvedUserId);
    }
}
