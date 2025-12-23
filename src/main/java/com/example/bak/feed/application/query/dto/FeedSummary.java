package com.example.bak.feed.application.query.dto;

import com.example.bak.community.application.query.dto.CommunityResult;
import com.example.bak.user.application.query.dto.UserResult;

/**
 * Feed 도메인의 간단한 정보를 담는 DTO 목록 조회 시 사용
 */
public record FeedSummary(
        Long id,
        String title,
        String contentPreview,
        UserResult author,
        CommunityResult.Detail community,
        int likeCount,
        int commentCount
) {

    public FeedSummary withContentPreview(String preview) {
        return new FeedSummary(
                id,
                title,
                preview,
                author,
                community,
                likeCount,
                commentCount
        );
    }
}
