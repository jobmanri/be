package com.example.bak.feed.application.query.dto;

import com.example.bak.community.application.query.dto.CommunityResult;
import java.util.List;

public record FeedCommunityTitleSection(
        Long communityId,
        String communityName,
        String jobGroup,
        List<FeedTitleOnly> feeds
) {

    public static FeedCommunityTitleSection of(CommunityResult.Detail community,
            List<FeedTitleOnly> feeds) {
        return new FeedCommunityTitleSection(
                community.id(),
                community.name(),
                community.jobGroup(),
                feeds
        );
    }
}
