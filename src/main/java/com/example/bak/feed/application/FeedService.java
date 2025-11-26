package com.example.bak.feed.application;

import com.example.bak.community.domain.Community;
import com.example.bak.feed.application.dto.FeedDetail;
import com.example.bak.feed.application.dto.FeedResult;
import com.example.bak.feed.application.dto.FeedSummary;
import com.example.bak.feed.application.port.CommunityCommandPort;
import com.example.bak.feed.domain.Feed;
import com.example.bak.feed.domain.FeedRepository;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.user.domain.User;
import com.example.bak.user.domain.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final CommunityCommandPort communityCommandPort;

    @Transactional
    public FeedResult createFeed(String title, String content, Long communityId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Community community = communityCommandPort.findById(communityId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMUNITY_NOT_FOUND));

        Feed newFeed = Feed.create(title, content, community, user);
        Feed savedFeed = feedRepository.save(newFeed);

        return FeedResult.of(savedFeed.getId());
    }

    @Transactional(readOnly = true)
    public FeedDetail getFeedDetail(Long feedId) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEED_NOT_FOUND));

        return FeedDetail.from(feed);
    }

    @Transactional(readOnly = true)
    public FeedSummary getFeedSummary(Long feedId) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEED_NOT_FOUND));

        return FeedSummary.from(feed);
    }

    @Transactional(readOnly = true)
    public List<FeedSummary> getFeeds(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Feed> feedPage = feedRepository.findAll(pageable);
        return FeedSummary.listFrom(feedPage);
    }
}
