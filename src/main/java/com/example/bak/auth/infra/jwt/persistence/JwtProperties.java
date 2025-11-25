package com.example.bak.auth.infra.jwt.persistence;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt.auth")
public class JwtProperties {
    private TokenProperties access = new TokenProperties();
    private TokenProperties refresh = new TokenProperties();

    @Data
    public static class TokenProperties {
        private String secretKey;
        private Long timeToLive;
    }
}
