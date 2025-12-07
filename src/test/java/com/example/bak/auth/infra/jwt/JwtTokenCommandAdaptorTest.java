package com.example.bak.auth.infra.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.auth.domain.TokenType;
import com.example.bak.global.security.dto.AuthInfo;
import com.example.bak.global.token.TestTokenProvider;
import com.example.bak.user.domain.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JwtTokenCommandAdaptorTest {

    @Nested
    @DisplayName("토큰 제작 Test Class")
    class JwtTokenGenerate {

        @Test
        @DisplayName("토큰 관련 속성 제작 성공 Case")
        void 토큰_관련_속성_제작_성공_케이스() {
            JwtTokenProvider provider = TestTokenProvider.getProvider();

            SecretKey secretKey = provider.getSecretKey(TokenType.ACCESS);
            SecretKey refreshSecretKey = provider.getSecretKey(TokenType.REFRESH);

            assertThat(secretKey).isNotNull();
            assertThat(secretKey.getAlgorithm()).isEqualTo("HmacSHA512");

            assertThat(refreshSecretKey).isNotNull();
            assertThat(refreshSecretKey.getAlgorithm()).isEqualTo("HmacSHA512");
        }

        @Test
        @DisplayName("토큰 제작 성공 케이스")
        void 토큰_제작_성공_케이스() {
            JwtTokenProvider provider = TestTokenProvider.getProvider();
            JwtTokenCommandAdaptor adaptor = new JwtTokenCommandAdaptor(provider);

            String accessToken = adaptor.publishToken(TokenType.ACCESS, 1L, UserRole.NORMAL);

            assertThat(accessToken).isNotNull();
        }

        @Test
        @DisplayName("")
        void 토큰_제작후_ID_케이스() {
            JwtTokenProvider provider = TestTokenProvider.getProvider();
            JwtTokenCommandAdaptor adaptor = new JwtTokenCommandAdaptor(provider);
            JwtValidator validator = new JwtValidator(provider);

            String accessToken = adaptor.publishToken(TokenType.ACCESS, 1L, UserRole.NORMAL);
            Jws<Claims> claims = validator.validate(TokenType.ACCESS, accessToken);
            AuthInfo info = AuthInfo.from(claims);
            assertThat(info).isNotNull();
            assertThat(info.id()).isEqualTo(1L);
        }
    }
}