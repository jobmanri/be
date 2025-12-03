package com.example.bak.feed.infra.command;

import com.example.bak.feed.application.command.port.FeedCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedCommandAdapter implements FeedCommandPort {
    private final FeedJpaRepository feedJpaRepository;
}
