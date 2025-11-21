package com.example.bak.auth.infra.jwt.persistence;

public sealed interface Token permits AccessToken, RefreshToken {
    Long timeToLive();
    String secretKey();
}
