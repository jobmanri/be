package com.example.bak.feed.infra.query.jdbc;

import com.example.bak.feed.application.query.dto.FeedDetail;
import com.example.bak.feed.application.query.dto.FeedSummary;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedJdbcRepository {

    Page<FeedSummary> findAll(Pageable pageable);

    Optional<FeedSummary> findSummaryById(Long feedId);

    Optional<FeedDetail> findDetailById(Long feedId);
}
