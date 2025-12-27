package com.example.bak.feed.infra.query.jdbc;

import com.example.bak.feed.application.query.dto.FeedDetail;
import com.example.bak.feed.application.query.dto.FeedSummary;
import com.example.bak.feed.application.query.dto.FeedTitleOnly;
import java.util.List;
import java.util.Optional;
public interface FeedJdbcRepository {

    List<FeedSummary> findRecentSummaries(int limit);

    Optional<FeedSummary> findSummaryById(Long feedId);

    Optional<FeedDetail> findDetailById(Long feedId);

    List<FeedTitleOnly> findTitleOnlyByCommunity(Long communityId, int limit);

    List<FeedDetail> findTopDetails(int limit);

    List<FeedTitleOnly> findTitleOnlyGroupedByCommunity(int limitPerCommunity);

    List<FeedSummary> findByCommunityIdWithCursor(Long communityId, Long cursorId, int size);
}
