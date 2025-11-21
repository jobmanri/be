package com.example.bak.feedcomment.domain;

import com.example.bak.global.support.AbstractStubRepository;
import java.util.List;
import java.util.Objects;

public class FeedCommentRepositoryStub
        extends AbstractStubRepository<Long, FeedComment>
        implements FeedCommentRepository {

    @Override
    protected Long getId(FeedComment feedComment) {
        return feedComment.getId();
    }

    @Override
    protected boolean isSame(Long left, Long right) {
        return Objects.equals(left, right);
    }

    @Override
    public List<FeedComment> findByFeedId(Long feedId) {
        return findAll().stream()
                .filter(comment -> comment.getFeed().getId().equals(feedId))
                .toList();
    }
}
