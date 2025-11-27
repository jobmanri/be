package com.example.bak.auth.application;

import com.example.bak.auth.domain.TokenType;
import com.example.bak.user.domain.UserRole;

public interface JwtTokenPort {

    /**
     * 지정된 토큰 타입과 사용자 정보를 기반으로 새로운 JWT(JSON Web Token)를 발급합니다.
     * <p>
     * 전달받은 {@code tokenType}에 따라 만료 시간(TTL)과 서명에 사용할 비밀키가 결정됩니다. 발급된 토큰은 {@code id}를 Subject(sub)로,
     * {@code role}을 비공개 클레임으로 포함합니다.
     * </p>
     *
     * @param tokenType 발급할 토큰의 유형 (예: ACCESS, REFRESH). 이 타입에 따라
     *                  {@link com.example.bak.auth.domain.JwtToken} 구현체가 선택됩니다.
     * @param id        토큰의 주체(Subject)가 되는 사용자 식별자(PK).
     * @param role      사용자의 권한 정보. 토큰 payload의 'role' 클레임에 저장됩니다.
     * @return Header, Payload, Signature가 결합된 Base64-URL 인코딩된 JWT 문자열
     * @throws Exception 서명 키가 유효하지 않거나, 토큰 생성 과정에서 암호화 오류가 발생한 경우
     */
    String publishToken(TokenType tokenType, Long id, UserRole role);
}
