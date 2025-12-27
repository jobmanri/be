package com.example.bak.feed.application.query;

import com.example.bak.feed.application.query.dto.FeedCommunityTitleSection;
import com.example.bak.feed.application.query.dto.FeedDetail;
import com.example.bak.feed.application.query.dto.FeedSummary;
import com.example.bak.feed.application.query.dto.FeedTitleOnly;
import com.example.bak.feed.application.query.port.FeedQueryPort;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedQueryService {

    private static final int CONTENT_PREVIEW_LIMIT = 100;

    private final FeedQueryPort feedQueryPort;

    public FeedDetail getFeedDetail(Long feedId) {
        return feedQueryPort.findDetailById(feedId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEED_NOT_FOUND));
    }

    public FeedSummary getFeedSummary(Long feedId) {
        return feedQueryPort.findSummaryById(feedId)
                .map(this::applyContentPreviewLimit)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEED_NOT_FOUND));
    }

    public List<FeedSummary> getFeeds(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FeedSummary> feedPage = feedQueryPort.findAll(pageable)
                .map(this::applyContentPreviewLimit);
        return feedPage.getContent();
    }

    public List<FeedDetail> getMainPageFeeds(int limit) {
        int sanitizedLimit = Math.max(1, limit);
        return feedQueryPort.findTopDetails(sanitizedLimit);
    }

    public CommunityPageResult getFeedsByCommunity(Long communityId, Long cursor, int size) {
        int sanitizedSize = Math.max(1, size);
        List<FeedSummary> feeds = feedQueryPort.findByCommunityIdWithCursor(communityId, cursor,
                        sanitizedSize)
                .stream()
                .map(this::applyContentPreviewLimit)
                .toList();

        Long nextCursor = feeds.isEmpty() ? null : feeds.get(feeds.size() - 1).id();
        return new CommunityPageResult(feeds, nextCursor);
    }

    public List<FeedCommunityTitleSection> getCommunityTitleSectionsForMainPage(
            int limitPerCommunity) {
        int sanitizedLimit = Math.max(1, limitPerCommunity);
        List<FeedTitleOnly> titles = feedQueryPort.findTitleOnlyGroupedByCommunity(sanitizedLimit);

        return titles.stream()
                .collect(Collectors.groupingBy(FeedTitleOnly::community))
                .entrySet().stream()
                .map(entry -> FeedCommunityTitleSection.of(entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<FeedTitleOnly> getFeedTitlesByCommunity(Long communityId, int limit) {
        int sanitizedLimit = Math.max(1, limit);
        return feedQueryPort.findTitleOnlyByCommunity(communityId, sanitizedLimit);
    }

    private FeedSummary applyContentPreviewLimit(FeedSummary summary) {
        String preview = summary.contentPreview();
        if (preview == null) {
            return summary.withContentPreview("");
        }

        if (preview.length() <= CONTENT_PREVIEW_LIMIT) {
            return summary;
        }

        return summary.withContentPreview(preview.substring(0, CONTENT_PREVIEW_LIMIT));
    }

    public record CommunityPageResult(List<FeedSummary> feeds, Long nextCursor) {

    }
}
