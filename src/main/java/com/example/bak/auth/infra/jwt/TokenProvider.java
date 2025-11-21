package com.example.bak.auth.infra.jwt;

import com.example.bak.auth.infra.jwt.persistence.*;
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
        tokens.put(TokenType.ACCESS, AccessToken.from(tokenProperties.access()));
        tokens.put(TokenType.REFRESH, RefreshToken.from(tokenProperties.refresh()));
    }

    public SecretKey getSecretKey(TokenType tokenType) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokens.get(tokenType).secretKey()));
    }

    public Long getTtl(TokenType tokenType) {
        return tokens.get(tokenType).timeToLive();
    }
}
