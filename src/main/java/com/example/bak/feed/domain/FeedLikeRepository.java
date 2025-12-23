package com.example.bak.feed.domain;

public interface FeedLikeRepository {

    FeedLike save(FeedLike feedLike);

    long countByFeedId(Long feedId);

    boolean existsByFeedIdAndUserId(Long feedId, Long userId);
}
