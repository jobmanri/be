package com.example.bak.feed.infra.persistence;

import com.example.bak.feed.domain.FeedComment;
import com.example.bak.feed.domain.FeedCommentRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FeedCommentJpaRepository extends JpaRepository<FeedComment, Long>,
        FeedCommentRepository {

    @Override
    FeedComment save(FeedComment comment);

    @Override
    Optional<FeedComment> findById(Long id);
}
