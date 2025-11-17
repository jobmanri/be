package com.example.bak.feed.application.dto;

import com.example.bak.company.application.dto.CommunityInfo;
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
        CommunityInfo community,
        int commentCount
) {

    public static FeedSummary from(Feed feed) {
        return new FeedSummary(
                feed.getId(),
                feed.getTitle(),
                UserInfo.from(feed.getAuthor()),
                CommunityInfo.from(feed.getCommunity()),
                feed.getComments().size()
        );
    }

    public static List<FeedSummary> listFrom(Page<Feed> page) {
        return page.getContent().stream()
                .map(FeedSummary::from)
                .toList();
    }
}