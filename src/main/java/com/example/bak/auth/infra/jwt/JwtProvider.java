package com.example.bak.auth.infra.jwt;

import com.example.bak.auth.application.JwtService;
import com.example.bak.auth.infra.jwt.persistence.TokenType;
import com.example.bak.global.common.utils.DateUtils;
import com.example.bak.user.domain.UserRole;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider implements JwtService {
    private final TokenProvider tokenProvider;
    private final static String tokenRoleKey = "role";

    @Override
    public String publishToken(TokenType tokenType, Long id, UserRole role) {
        return Jwts.builder()
                .subject(id.toString())
                .claim(tokenRoleKey, role)
                .signWith(tokenProvider.getSecretKey(tokenType))
                .issuedAt(DateUtils.getNow())
                .expiration(DateUtils.getExpirationDate(tokenProvider.getTtl(tokenType)))
                .compact();
    }

    @Override
    public Long getId(TokenType tokenType, String token) {
        return Long.parseLong(
                Jwts.parser()
                        .verifyWith(tokenProvider.getSecretKey(tokenType))
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getSubject()
        );
    }

    @Override
    public UserRole getRole(TokenType tokenType, String token) {
        return UserRole.convert(
                Jwts.parser()
                        .verifyWith(tokenProvider.getSecretKey(tokenType))
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .get(tokenRoleKey)
                        .toString()
        );
    }
}
