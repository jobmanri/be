package com.example.bak.feed.presentation.dto;

import com.example.bak.feed.application.query.dto.FeedTitleOnly;
import java.util.List;

public record MainPageTitlesResponse(List<CommunitySection> communities) {

    public record CommunitySection(
            Long communityId,
            String communityName,
            String jobGroup,
            List<FeedTitleOnly> feeds
    ) {
    }
}
