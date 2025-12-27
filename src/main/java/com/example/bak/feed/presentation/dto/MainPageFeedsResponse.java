package com.example.bak.feed.presentation.dto;

import com.example.bak.feed.application.query.dto.FeedDetail;
import java.util.List;

public record MainPageFeedsResponse(List<FeedDetail> feeds) {
}
