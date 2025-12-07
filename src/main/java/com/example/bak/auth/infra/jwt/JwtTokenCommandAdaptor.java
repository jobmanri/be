package com.example.bak.auth.infra.jwt;

import com.example.bak.auth.application.command.port.JwtTokenCommandPort;
import com.example.bak.auth.domain.TokenType;
import com.example.bak.global.common.utils.DateUtils;
import com.example.bak.user.domain.UserRole;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenCommandAdaptor implements JwtTokenCommandPort {

    private final JwtTokenProvider tokenProvider;
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
