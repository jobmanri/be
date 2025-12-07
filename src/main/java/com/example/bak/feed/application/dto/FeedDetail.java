package com.example.bak.feed.application.dto;

import com.example.bak.community.application.query.dto.CommunityResult;
import com.example.bak.feed.domain.Feed;
import com.example.bak.user.application.query.dto.UserResult;

/**
 * Feed 도메인의 상세 정보를 담는 DTO 단건 조회 시 사용
 */
public record FeedDetail(
        Long id,
        String title,
        String content,
        UserResult author,
        CommunityResult.Detail community
) {

    public static FeedDetail from(Feed feed) {
        final UserResult author = UserResult.from(feed.getAuthor());
        final CommunityResult.Detail community = CommunityResult.Detail.from(feed.getCommunity());

        return new FeedDetail(
                feed.getId(),
                feed.getTitle(),
                feed.getContent(),
                author,
                community
        );
    }
}