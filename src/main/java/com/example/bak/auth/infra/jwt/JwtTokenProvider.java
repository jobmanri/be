package com.example.bak.auth.infra.jwt;

import com.example.bak.auth.domain.JwtToken;
import com.example.bak.auth.domain.TokenType;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final Map<TokenType, JwtToken> tokens = new EnumMap<>(TokenType.class);

    public JwtTokenProvider(List<JwtToken> tokens) {
        for (JwtToken token : tokens) {
            this.tokens.put(token.getTokenType(), token);
        }
    }

    public SecretKey getSecretKey(TokenType tokenType) {
        byte[] secretKeyBytes = Decoders.BASE64.decode(tokens.get(tokenType).getSecretKey());
        return Keys.hmacShaKeyFor(secretKeyBytes);
    }

    public Long getTtl(TokenType tokenType) {
        return tokens.get(tokenType).getTimeToLive();
    }
}
