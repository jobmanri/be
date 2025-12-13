package com.example.bak.feed.domain;

import com.example.bak.feed.application.command.port.FeedCommandPort;
import com.example.bak.feed.application.command.port.FeedValidationPort;
import com.example.bak.global.support.AbstractStubRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FeedRepositoryStub
        extends AbstractStubRepository<Long, Feed>
        implements FeedRepository, FeedCommandPort, FeedValidationPort {

    @Override
    protected Long getId(Feed feed) {
        return feed.getId();
    }

    @Override
    protected boolean isSame(Long left, Long right) {
        return Objects.equals(left, right);
    }

    @Override
    public List<Feed> findAll() {
        return super.findAll();
    }

    @Override
    public Page<Feed> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    @Override
    public Feed save(Feed feed) {
        return super.save(feed);
    }

    @Override
    public Optional<Feed> findById(Long id) {
        return super.findById(id);
    }

    @Override
    public void delete(Feed feed) {
        store.removeIf(it -> isSame(getId(it), getId(feed)));
    }

    @Override
    public boolean existsById(Long feedId) {
        return findById(feedId).isPresent();
    }
}
