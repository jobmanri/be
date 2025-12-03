package com.example.bak.feed.application.command.port;

import com.example.bak.feed.domain.Feed;
import java.util.Optional;

public interface FeedCommandPort {

    Feed save(Feed feed);

    Optional<Feed> findById(Long id);
}
