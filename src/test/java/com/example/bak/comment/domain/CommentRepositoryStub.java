package com.example.bak.comment.domain;

import com.example.bak.comment.application.command.port.CommentCommandPort;
import com.example.bak.comment.application.query.dto.CommentInfo;
import com.example.bak.comment.application.query.port.CommentQueryPort;
import com.example.bak.global.support.AbstractStubRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CommentRepositoryStub
        extends AbstractStubRepository<Long, Comment>
        implements CommentCommandPort, CommentQueryPort {

    @Override
    protected Long getId(Comment comment) {
        return comment.getId();
    }

    @Override
    protected boolean isSame(Long left, Long right) {
        return Objects.equals(left, right);
    }

    @Override
    public List<CommentInfo> findByFeedId(Long feedId) {
        return findAll().stream()
                .filter(comment -> comment.getFeedId().equals(feedId))
                .map(CommentInfo::from)
                .toList();
    }

    @Override
    public Optional<Comment> findById(Long commentId) {
        return super.findById(commentId);
    }
}
