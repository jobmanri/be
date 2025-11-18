package com.example.bak.feedcomment.presentation.dto;

public record CommentRequest(
        String content,
        Long userId
) {

}
