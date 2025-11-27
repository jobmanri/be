package com.example.bak.auth.domain;

public sealed interface JwtToken permits AccessToken, RefreshToken {

    String getSecretKey();

    Long getTimeToLive();

    TokenType getTokenType();
}
