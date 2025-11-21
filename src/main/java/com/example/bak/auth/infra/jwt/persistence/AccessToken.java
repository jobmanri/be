package com.example.bak.auth.infra.jwt.persistence;

public record AccessToken(String secretKey, Long timeToLive) implements Token {
    public static AccessToken from(JwtProperties.TokenProperties tokenProperties){
        return new AccessToken(tokenProperties.secretKey(), tokenProperties.timeToLive());
    }
}
