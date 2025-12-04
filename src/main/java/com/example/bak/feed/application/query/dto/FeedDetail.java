package com.example.bak.feed.application.query.dto;

import com.example.bak.community.application.query.dto.CommunityResult;
import com.example.bak.user.application.dto.UserInfo;

/**
 * Feed 도메인의 상세 정보를 담는 DTO 단건 조회 시 사용
 */
public record FeedDetail(
        Long id,
        String title,
        String content,
        UserInfo author,
        CommunityResult.Detail community
) {
}
