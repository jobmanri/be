package com.example.bak.comment.infra.persistence;

import com.example.bak.comment.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByFeedId(Long feedId);
}
