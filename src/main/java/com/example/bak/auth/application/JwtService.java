package com.example.bak.auth.application;

import com.example.bak.auth.infra.jwt.persistence.TokenType;
import com.example.bak.user.domain.UserRole;

public interface JwtService {
    String publishToken(TokenType tokenType, Long id, UserRole role);
    Long getId(TokenType tokenType, String token);
    UserRole getRole(TokenType tokenType, String token);
}
