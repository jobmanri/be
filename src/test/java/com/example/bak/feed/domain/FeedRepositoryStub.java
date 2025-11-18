package com.example.bak.feed.domain;

import com.example.bak.global.support.AbstractStubRepository;
import java.util.Objects;

public class FeedRepositoryStub
        extends AbstractStubRepository<Long, Feed>
        implements FeedRepository {

    @Override
    protected Long getId(Feed feed) {
        return feed.getId();
    }

    @Override
    protected boolean isSame(Long left, Long right) {
        return Objects.equals(left, right);
    }
}
