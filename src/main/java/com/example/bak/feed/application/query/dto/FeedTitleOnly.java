package com.example.bak.feed.application.query.dto;

import com.example.bak.community.application.query.dto.CommunityResult;

public record FeedTitleOnly(
        Long id,
        String title,
        int likeCount,
        int commentCount,
        CommunityResult.Detail community
) {

}
