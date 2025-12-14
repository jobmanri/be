package com.example.bak.feed.application.command;

import com.example.bak.feed.application.command.dto.FeedResult;
import com.example.bak.feed.application.command.port.CommunityValidationPort;
import com.example.bak.feed.application.command.port.FeedCommandPort;
import com.example.bak.feed.domain.Feed;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedCommandService {

    private final FeedCommandPort feedCommandPort;
    private final CommunityValidationPort communityValidationPort;

    public FeedResult createFeed(String title, String content, Long communityId, Long userId) {
        if (!communityValidationPort.isCommunityExists(communityId)) {
            throw new BusinessException(ErrorCode.COMMUNITY_NOT_FOUND);
        }

        Feed newFeed = Feed.create(title, content, communityId, userId);
        Feed savedFeed = feedCommandPort.save(newFeed);

        return FeedResult.of(savedFeed.getId());
    }

    public void updateFeed(Long feedId, String title, String content, Long userId) {
        Feed feed = feedCommandPort.findById(feedId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEED_NOT_FOUND));

        feed.validateAuthor(userId);

        feed.update(title, content);
        feedCommandPort.save(feed);
    }

    public void deleteFeed(Long feedId, Long userId) {
        Feed feed = feedCommandPort.findById(feedId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEED_NOT_FOUND));

        feed.validateAuthor(userId);

        feedCommandPort.delete(feed);
    }
}
