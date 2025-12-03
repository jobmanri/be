package com.example.bak.feed.application.command;

import com.example.bak.community.domain.Community;
import com.example.bak.feed.application.command.port.CommunityCommandPort;
import com.example.bak.feed.application.command.port.FeedCommandPort;
import com.example.bak.feed.application.query.dto.FeedResult;
import com.example.bak.feed.domain.Feed;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.user.domain.User;
import com.example.bak.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedCommandService {

    private final FeedCommandPort feedCommandPort;
    private final UserRepository userRepository;
    private final CommunityCommandPort communityCommandPort;

    public FeedResult createFeed(String title, String content, Long communityId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Community community = communityCommandPort.findById(communityId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMUNITY_NOT_FOUND));

        Feed newFeed = Feed.create(title, content, community, user);
        Feed savedFeed = feedCommandPort.save(newFeed);

        return FeedResult.of(savedFeed.getId());
    }
}
