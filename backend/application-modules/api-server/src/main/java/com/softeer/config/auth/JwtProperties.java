package com.softeer.config.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(long accessTokenExpirationMs, String secretKey) {

}
