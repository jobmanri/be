package com.example.bak.feed.application.query.dto;

import java.util.List;

public record FeedCommunityPageResult(
        List<FeedSummary> feeds,
        Long nextCursor
) {
}
