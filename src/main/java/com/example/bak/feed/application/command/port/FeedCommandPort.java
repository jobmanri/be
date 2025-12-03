package com.example.bak.feed.application.command.port;

import com.example.bak.feed.domain.Feed;

public interface FeedCommandPort {

    public Feed save(Feed feed);
}
