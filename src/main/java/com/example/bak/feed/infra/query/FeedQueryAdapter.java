package com.example.bak.feed.infra.query;

import com.example.bak.feed.application.query.dto.FeedDetail;
import com.example.bak.feed.application.query.dto.FeedSummary;
import com.example.bak.feed.application.query.dto.FeedTitleOnly;
import com.example.bak.feed.application.query.port.FeedQueryPort;
import com.example.bak.feed.infra.query.jdbc.FeedJdbcRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedQueryAdapter implements FeedQueryPort {

    private final FeedJdbcRepository feedJdbcRepository;

    @Override
    public Page<FeedSummary> findAll(Pageable pageable) {
        return feedJdbcRepository.findAll(pageable);
    }

    @Override
    public Optional<FeedSummary> findSummaryById(Long feedId) {
        return feedJdbcRepository.findSummaryById(feedId);
    }

    @Override
    public Optional<FeedDetail> findDetailById(Long feedId) {
        return feedJdbcRepository.findDetailById(feedId);
    }

    @Override
    public List<FeedTitleOnly> findTitleOnlyByCommunity(Long communityId, int limit) {
        return feedJdbcRepository.findTitleOnlyByCommunity(communityId, limit);
    }

    @Override
    public List<FeedDetail> findTopDetails(int limit) {
        return feedJdbcRepository.findTopDetails(limit);
    }

    @Override
    public List<FeedTitleOnly> findTitleOnlyGroupedByCommunity(int limitPerCommunity) {
        return feedJdbcRepository.findTitleOnlyGroupedByCommunity(limitPerCommunity);
    }

    @Override
    public List<FeedSummary> findByCommunityIdWithCursor(Long communityId, Long cursorId, int size) {
        return feedJdbcRepository.findByCommunityIdWithCursor(communityId, cursorId, size);
    }
}
