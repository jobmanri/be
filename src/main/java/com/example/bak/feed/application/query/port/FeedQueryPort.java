package com.example.bak.feed.application.query.port;

import com.example.bak.feed.application.query.dto.FeedDetail;
import com.example.bak.feed.application.query.dto.FeedSummary;
import com.example.bak.feed.application.query.dto.FeedTitleOnly;
import java.util.Optional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedQueryPort {

    Page<FeedSummary> findAll(Pageable pageable);

    Optional<FeedSummary> findSummaryById(Long feedId);

    Optional<FeedDetail> findDetailById(Long feedId);

    List<FeedTitleOnly> findTitleOnlyByCommunity(Long communityId, int limit);
}
