package com.example.bak.auth.infra.jwt.persistence;

public sealed interface Token permits AccessTokenProperties, RefreshTokenProperties {
    Long timeToLive();
    String secretKey();
}
