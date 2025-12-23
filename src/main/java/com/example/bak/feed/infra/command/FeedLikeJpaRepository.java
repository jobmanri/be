package com.example.bak.feed.infra.command;

import com.example.bak.feed.domain.FeedLike;
import com.example.bak.feed.domain.FeedLikeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedLikeJpaRepository extends JpaRepository<FeedLike, Long>, FeedLikeRepository {

    @Override
    long countByFeedId(Long feedId);

    @Override
    boolean existsByFeedIdAndUserId(Long feedId, Long userId);
}
