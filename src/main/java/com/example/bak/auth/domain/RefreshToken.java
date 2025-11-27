package com.example.bak.auth.domain;

import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Component
@ConfigurationProperties(prefix = "jwt.auth.refresh")
public final class RefreshToken implements JwtToken {

    private String secretKey;
    private Long timeToLive;

    @PostConstruct
    public void validate() {
        if (timeToLive == null || timeToLive <= 0) {
            throw new IllegalStateException("timeToLive must be greater than 0");
        }

        if (secretKey == null || secretKey.isBlank()) {
            throw new IllegalStateException("secretKey must not be blank");
        }
    }

    @Override
    public String getSecretKey() {
        return this.secretKey;
    }

    @Override
    public Long getTimeToLive() {
        return this.timeToLive;
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.REFRESH;
    }
}
