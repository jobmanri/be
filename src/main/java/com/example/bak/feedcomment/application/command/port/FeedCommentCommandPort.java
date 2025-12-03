package com.example.bak.feedcomment.application.command.port;

import com.example.bak.feedcomment.domain.FeedComment;
import java.util.Optional;

public interface FeedCommentCommandPort {

    FeedComment save(FeedComment comment);

    Optional<FeedComment> findById(Long commentId);
}

