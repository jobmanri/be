package com.example.bak.auth.infra.jwt.persistence;

public record AccessTokenProperties(String secretKey, Long timeToLive) implements Token {
    public static AccessTokenProperties from(JwtProperties.TokenProperties tokenProperties){
        return new AccessTokenProperties(tokenProperties.secretKey(), tokenProperties.timeToLive());
    }
}
