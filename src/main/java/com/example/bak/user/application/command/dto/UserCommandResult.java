package com.example.bak.user.application.command.dto;

public record UserCommandResult(
        Long id
) {

    public static UserCommandResult of(Long id) {
        return new UserCommandResult(id);
    }
}
