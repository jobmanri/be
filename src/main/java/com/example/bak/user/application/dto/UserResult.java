package com.example.bak.user.application.dto;

public record UserResult(
        Long id
) {

    public static UserResult of(Long id) {
        return new UserResult(id);
    }
}
