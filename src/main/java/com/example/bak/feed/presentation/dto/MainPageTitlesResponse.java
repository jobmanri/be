package com.example.bak.feed.presentation.dto;

import com.example.bak.feed.application.query.dto.FeedCommunityTitleSection;
import java.util.List;

public record MainPageTitlesResponse(List<FeedCommunityTitleSection> communities) {
}
