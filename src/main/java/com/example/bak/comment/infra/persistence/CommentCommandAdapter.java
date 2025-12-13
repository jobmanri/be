package com.example.bak.comment.infra.persistence;

import com.example.bak.comment.application.command.port.CommentCommandPort;
import com.example.bak.comment.domain.Comment;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentCommandAdapter implements CommentCommandPort {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(comment);
    }

    @Override
    public Optional<Comment> findById(Long commentId) {
        return commentJpaRepository.findById(commentId);
    }
}
