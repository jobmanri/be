package com.example.bak.comment.application.command.port;

import com.example.bak.comment.domain.Comment;
import java.util.Optional;

public interface CommentCommandPort {

    Comment save(Comment comment);

    Optional<Comment> findById(Long commentId);
}
