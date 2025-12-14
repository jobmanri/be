package com.example.bak.feed.presentation.dto;

public record FeedRequest(
        String title,
        String content,
        Long communityId
) {

}