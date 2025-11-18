package com.example.bak.user.application.dto;

import com.example.bak.user.domain.User;

/**
 * User 도메인의 기본 정보를 담는 DTO
 * 다른 도메인에서 User 정보가 필요할 때 재사용 가능
 */
public record UserInfo(
        Long id,
        String nickname
) {

    public static UserInfo from(User user) {
        return new UserInfo(
                user.getId(),
                user.getProfile().getNickname()
        );
    }
}
