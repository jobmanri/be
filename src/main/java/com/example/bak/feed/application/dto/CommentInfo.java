package com.example.bak.feed.application.dto;

import com.example.bak.feed.domain.FeedComment;

/**
 * FeedComment의 기본 정보를 담는 DTO Feed 상세 조회 시 포함됨
 */
public record CommentInfo(
        Long id,
        String author,
        String content
) {

    public static CommentInfo from(FeedComment comment) {
        return new CommentInfo(
                comment.getId(),
                comment.getAuthor(),
                comment.getComment()
        );
    }
}