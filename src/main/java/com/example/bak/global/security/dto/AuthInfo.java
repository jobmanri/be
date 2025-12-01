package com.example.bak.global.security.dto;

import com.example.bak.user.domain.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public record AuthInfo(
        Long id,
        UserRole role
) {
    private final static String tokenRoleKey = "role";

    public static AuthInfo from(Jws<Claims> jws) {
        return new AuthInfo(
                Long.parseLong(jws.getPayload().getSubject()),
                UserRole.convert(jws.getPayload().get(tokenRoleKey).toString())
        );
    }
}
