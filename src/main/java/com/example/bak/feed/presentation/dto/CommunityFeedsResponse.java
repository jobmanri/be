package com.example.bak.feed.presentation.dto;

import com.example.bak.feed.application.query.dto.FeedSummary;
import java.util.List;

public record CommunityFeedsResponse(
        List<FeedSummary> feeds,
        Long nextCursor
) {
}
