package com.example.bak.community.application.dto;

import com.example.bak.community.domain.Community;

public record CommunityResult() {

    public static CommunityResult from(Community community) {
        return new CommunityResult();
    }
}
