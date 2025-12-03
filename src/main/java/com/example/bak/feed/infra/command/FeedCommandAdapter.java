package com.example.bak.feed.infra.command;

import com.example.bak.feed.application.command.port.FeedCommandPort;
import com.example.bak.feed.domain.Feed;
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
}
