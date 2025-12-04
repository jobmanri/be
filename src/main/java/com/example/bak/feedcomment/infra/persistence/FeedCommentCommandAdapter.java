package com.example.bak.feedcomment.infra.persistence;

import com.example.bak.feedcomment.application.command.port.FeedCommentCommandPort;
import com.example.bak.feedcomment.domain.FeedComment;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedCommentCommandAdapter implements FeedCommentCommandPort {

    private final FeedCommentJpaRepository feedCommentJpaRepository;

    @Override
    public FeedComment save(FeedComment comment) {
        return feedCommentJpaRepository.save(comment);
    }

    @Override
    public Optional<FeedComment> findById(Long commentId) {
        return feedCommentJpaRepository.findById(commentId);
    }
}

