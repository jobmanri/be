package com.example.bak.comment.infra.persistence;

import com.example.bak.comment.application.command.port.CommentValidationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentValidationAdapter implements CommentValidationPort {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public boolean existsById(Long commentId) {
        return commentJpaRepository.existsById(commentId);
    }
}
