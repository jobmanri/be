package com.example.bak.user.application.dto;

import com.example.bak.user.domain.Profile;

public record ProfileResult(
        String name,
        String nickname
) {

    public static ProfileResult from(Profile profile) {
        return new ProfileResult(profile.getName(), profile.getNickname());
    }
}
