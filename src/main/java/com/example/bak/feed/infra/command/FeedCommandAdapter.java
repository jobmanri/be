package com.example.bak.feed.infra.command;

import com.example.bak.feed.application.command.port.FeedCommandPort;
import com.example.bak.feed.domain.Feed;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedCommandAdapter implements FeedCommandPort {

    private final FeedJpaRepository feedJpaRepository;

    @Override
    public Feed save(Feed feed) {
        return feedJpaRepository.save(feed);
    }

    @Override
    public Optional<Feed> findById(Long id) {
        return feedJpaRepository.findById(id);
    }
}
