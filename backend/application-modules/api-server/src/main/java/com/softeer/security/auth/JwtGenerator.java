package com.softeer.security.auth;

import com.softeer.entity.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@EnableConfigurationProperties(JwtProperties.class)
@RequiredArgsConstructor
public class JwtGenerator {

    private static final String ROLE = "role";

    private final JwtProperties jwtProperties;
    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes());
    }

    public Token createAccessToken(long userId, Role role)  { return createToken(userId, role, jwtProperties.accessTokenExpirationMs()); }

    private Token createToken(long userId, Role role, long validityMs) {
        Date now    = new Date();
        Date expiry = new Date(now.getTime() + validityMs);

        String jwt = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim(ROLE, role.name())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new Token(jwt);   // Date 타입으로 반환
    }
}
