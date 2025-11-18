package com.example.bak.feed.domain;

import java.util.Optional;

public interface FeedCommentRepository {

    FeedComment save(FeedComment comment);

    Optional<FeedComment> findById(Long id);
}
