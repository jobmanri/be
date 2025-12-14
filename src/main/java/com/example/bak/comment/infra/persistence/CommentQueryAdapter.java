package com.example.bak.comment.infra.persistence;

import com.example.bak.comment.application.query.dto.CommentInfo;
import com.example.bak.comment.application.query.port.CommentQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentQueryAdapter implements CommentQueryPort {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public List<CommentInfo> findByFeedId(Long feedId) {
        return commentJpaRepository.findByFeedId(feedId).stream()
                .map(CommentInfo::from)
                .toList();
    }
}
