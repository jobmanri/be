package com.example.bak.auth.application;

import com.example.bak.auth.infra.jwt.persistence.TokenType;
import com.example.bak.user.domain.UserRole;

public interface JwtPort {
    String publishToken(TokenType tokenType, Long id, UserRole role);
}
