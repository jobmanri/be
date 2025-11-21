package com.example.bak.auth.infra.jwt.persistence;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt.auth")
public record JwtProperties(
    TokenProperties access,
    TokenProperties refresh
) {
    public record TokenProperties(
            String secretKey,
            Long timeToLive
    ){
    }
}
