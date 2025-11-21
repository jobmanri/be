package com.example.bak.auth.infra.jwt.persistence;

public record RefreshToken(String secretKey, Long timeToLive) implements Token{
    public static RefreshToken from(JwtProperties.TokenProperties tokenProperties){
        return new RefreshToken(tokenProperties.secretKey(), tokenProperties.timeToLive());
    }
}
