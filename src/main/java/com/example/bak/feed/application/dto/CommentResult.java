package com.example.bak.feed.application.dto;

public record CommentResult(
        Long id
) {

    public static CommentResult of(Long id) {
        return new CommentResult(id);
    }
}
