package com.example.bak.auth.presentation.dto;

public record LoginRequest(
        String email,
        String password
) {
}
