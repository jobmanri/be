package com.example.bak.auth.infra.jwt;

import com.example.bak.auth.infra.jwt.persistence.TokenType;
import com.example.bak.auth.infra.jwt.persistence.AccessTokenProperties;
import com.example.bak.auth.infra.jwt.persistence.JwtProperties;
import com.example.bak.auth.infra.jwt.persistence.RefreshTokenProperties;
import com.example.bak.auth.infra.jwt.persistence.Token;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenProvider {
    private final Map<TokenType, Token> tokens = new HashMap<>();

    public TokenProvider(JwtProperties tokenProperties) {
        tokens.put(TokenType.ACCESS, AccessTokenProperties.from(tokenProperties.access()));
        tokens.put(TokenType.REFRESH, RefreshTokenProperties.from(tokenProperties.refresh()));
    }

    public SecretKey getSecretKey(TokenType tokenType) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokens.get(tokenType).secretKey()));
    }

    public Long getTtl(TokenType tokenType) {
        return tokens.get(tokenType).timeToLive();
    }
}
