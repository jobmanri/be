package com.example.bak.auth.application.dto;

public record TokenInfo(
        String accessToken,
        String refreshToken
) {
    public static TokenInfo of(String accessToken, String refreshToken) {
        return new TokenInfo(accessToken, refreshToken);
    }
}
