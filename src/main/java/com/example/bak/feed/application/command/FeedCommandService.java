package com.example.bak.feed.application.command;

import com.example.bak.feed.application.command.port.FeedCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedCommandService {
    private final FeedCommandPort feedCommandPort;
}
