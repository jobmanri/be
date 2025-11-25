package com.example.bak.auth.infra.jwt;

import com.example.bak.auth.application.JwtPort;
import com.example.bak.auth.infra.jwt.persistence.TokenType;
import com.example.bak.global.common.utils.DateUtils;
import com.example.bak.user.domain.UserRole;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAdaptor implements JwtPort {
    private final TokenProvider tokenProvider;
    private final static String TOKEN_ROLE_KEY = "role";

    @Override
    public String publishToken(TokenType tokenType, Long id, UserRole role) {
        return Jwts.builder()
                .subject(id.toString())
                .claim(TOKEN_ROLE_KEY, role)
                .signWith(tokenProvider.getSecretKey(tokenType))
                .issuedAt(DateUtils.getNow())
                .expiration(DateUtils.getExpirationDate(tokenProvider.getTtl(tokenType)))
                .compact();
    }
}
