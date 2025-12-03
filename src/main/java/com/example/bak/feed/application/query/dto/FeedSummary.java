package com.example.bak.feed.application.query.dto;

import com.example.bak.community.application.query.dto.CommunityResult;
import com.example.bak.feed.domain.Feed;
import com.example.bak.user.application.dto.UserInfo;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * Feed 도메인의 간단한 정보를 담는 DTO 목록 조회 시 사용
 */
public record FeedSummary(
        Long id,
        String title,
        UserInfo author,
        CommunityResult.Detail community,
        int commentCount
) {

    public static FeedSummary from(Feed feed) {
        final UserInfo author = UserInfo.from(feed.getAuthor());
        final CommunityResult.Detail community = CommunityResult.Detail.from(feed.getCommunity());
        final int commentCount = feed.getComments().size();

        return new FeedSummary(
                feed.getId(),
                feed.getTitle(),
                author,
                community,
                commentCount
        );
    }

    public static List<FeedSummary> listFrom(Page<Feed> page) {
        return page.getContent().stream()
                .map(FeedSummary::from)
                .toList();
    }
}