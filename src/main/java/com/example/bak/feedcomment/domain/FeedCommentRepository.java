package com.example.bak.feedcomment.domain;

import java.util.List;
import java.util.Optional;

public interface FeedCommentRepository {

    FeedComment save(FeedComment comment);

    List<FeedComment> findByFeedId(Long feedId);

    Optional<FeedComment> findById(Long id);
}
