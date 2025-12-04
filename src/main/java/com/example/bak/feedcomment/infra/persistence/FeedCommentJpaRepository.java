package com.example.bak.feedcomment.infra.persistence;

import com.example.bak.feedcomment.domain.FeedComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCommentJpaRepository extends JpaRepository<FeedComment, Long> {

    List<FeedComment> findByFeedId(Long feedId);
}
