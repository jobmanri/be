package com.example.bak.auth.infra.jwt.persistence;

public record RefreshTokenProperties(String secretKey, Long timeToLive) implements Token{
    public static RefreshTokenProperties from(JwtProperties.TokenProperties tokenProperties){
        return new RefreshTokenProperties(tokenProperties.getSecretKey(), tokenProperties.getTimeToLive());
    }
}
