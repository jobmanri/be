package com.example.bak.feedcomment.infra.persistence;

import com.example.bak.feedcomment.application.query.dto.CommentInfo;
import com.example.bak.feedcomment.application.query.port.FeedCommentQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedCommentQueryAdapter implements FeedCommentQueryPort {

    private final FeedCommentJpaRepository feedCommentJpaRepository;

    @Override
    public List<CommentInfo> findByFeedId(Long feedId) {
        return feedCommentJpaRepository.findByFeedId(feedId).stream()
                .map(CommentInfo::from)
                .toList();
    }
}

