package com.example.bak.comment.application.query.dto;

import com.example.bak.comment.domain.Comment;

/**
 * Comment의 기본 정보를 담는 DTO Feed 상세 조회 시 포함됨
 */
public record CommentInfo(
        Long id,
        Long authorId,
        String authorName,
        String content
) {

    public static CommentInfo from(Comment comment) {
        return new CommentInfo(
                comment.getId(),
                comment.getAuthorId(),
                comment.getAuthorNickname(),
                comment.getContent()
        );
    }
}
