package com.example.bak.comment.application.command.port;

public interface CommentValidationPort {

    boolean existsById(Long commentId);
}
