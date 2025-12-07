package com.example.bak.user.application.query.dto;

import com.example.bak.user.domain.User;

public record UserResult(
        Long id,
        String nickname
) {

    /**
     * @Param - id: User Id
     * @Param - nickname: User's Profile nickname
     *
     */
    public static UserResult from(Long id, String nickname) {
        return new UserResult(
                id,
                nickname
        );
    }

    public static UserResult from(User user) {
        return new UserResult(
                user.getId(),
                user.getProfile().getNickname()
        );
    }
}
