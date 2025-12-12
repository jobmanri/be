package com.example.bak.user.application.query.dto;

public record UserResult(
        Long id,
        String nickname
) {

    public static UserResult from(Long id, String nickname) {
        return new UserResult(
                id,
                nickname
        );
    }

}
