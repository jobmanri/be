package com.example.bak.feedcomment.domain;

import com.example.bak.feedcomment.application.command.port.FeedCommentCommandPort;
import com.example.bak.feedcomment.application.query.dto.CommentInfo;
import com.example.bak.feedcomment.application.query.port.FeedCommentQueryPort;
import com.example.bak.global.support.AbstractStubRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FeedCommentRepositoryStub
        extends AbstractStubRepository<Long, FeedComment>
        implements FeedCommentCommandPort, FeedCommentQueryPort {

    @Override
    protected Long getId(FeedComment feedComment) {
        return feedComment.getId();
    }

    @Override
    protected boolean isSame(Long left, Long right) {
        return Objects.equals(left, right);
    }

    @Override
    public List<CommentInfo> findByFeedId(Long feedId) {
        return findAll().stream()
                .filter(comment -> comment.getFeed().getId().equals(feedId))
                .map(CommentInfo::from)
                .toList();
    }

    @Override
    public Optional<FeedComment> findById(Long commentId) {
        return super.findById(commentId);
    }
}
