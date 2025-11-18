package com.example.bak.feed.presentation.dto;

public record CommentRequest(
        String content,
        Long userId
) {

}
