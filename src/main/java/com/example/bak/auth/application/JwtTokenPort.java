package com.example.bak.auth.application;

import com.example.bak.auth.domain.TokenType;
import com.example.bak.user.domain.UserRole;

public interface JwtTokenPort {

    String publishToken(TokenType tokenType, Long id, UserRole role);
}
