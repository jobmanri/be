package com.example.bak.auth.infra.jwt.utils;

import java.util.Optional;

public class JwtUtils {

    public final static String PREFIX_BEARER_TOKEN = "Bearer ";
    public final static String CLAIMS_KEY = "claims";

    /**
     * HttpServletRequest의 Authorization 헤더 값에서 실제 JWT 토큰 문자열을 추출합니다.
     * <p>
     * 입력된 값이 비어있지 않고 'Bearer '로 시작하는 경우, 접두사를 제외한 실제 토큰 값을 반환합니다. 조건에 맞지 않는 경우 {@code null}을
     * 반환합니다.
     *
     * @param bearerToken Authorization 헤더에서 전달된 Bearer 토큰 문자열
     * @return 'Bearer ' 접두사가 제거된 순수 토큰 문자열, 또는 유효하지 않은 경우 {@code null}
     */
    public static Optional<String> resolveToken(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith(PREFIX_BEARER_TOKEN)) {
            return Optional.empty();
        }
        String token = bearerToken.substring(PREFIX_BEARER_TOKEN.length());
        return Optional.of(token);
    }
}
