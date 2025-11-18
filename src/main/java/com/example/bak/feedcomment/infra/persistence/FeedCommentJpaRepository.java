package com.example.bak.feedcomment.infra.persistence;

import com.example.bak.feedcomment.domain.FeedComment;
import com.example.bak.feedcomment.domain.FeedCommentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FeedCommentJpaRepository extends JpaRepository<FeedComment, Long>,
        FeedCommentRepository {

    @Override
    FeedComment save(FeedComment comment);
    
    @Override
    List<FeedComment> findByFeedId(Long feedId);

    @Override
    Optional<FeedComment> findById(Long id);
}
