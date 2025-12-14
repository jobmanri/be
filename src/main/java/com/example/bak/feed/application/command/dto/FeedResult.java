package com.example.bak.feed.application.command.dto;

public record FeedResult(
        Long id
) {

    public static FeedResult of(Long id) {
        return new FeedResult(id);
    }
}
