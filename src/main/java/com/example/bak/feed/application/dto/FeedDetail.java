package com.example.bak.feed.application.dto;

import com.example.bak.company.application.dto.CommunityInfo;
import com.example.bak.feed.domain.Feed;
import com.example.bak.user.application.dto.UserInfo;
import java.util.List;

/**
 * Feed 도메인의 상세 정보를 담는 DTO 단건 조회 시 사용
 */
public record FeedDetail(
        Long id,
        String title,
        String content,
        UserInfo author,
        CommunityInfo community,
        List<CommentInfo> comments
) {

    public static FeedDetail from(Feed feed) {
        return new FeedDetail(
                feed.getId(),
                feed.getTitle(),
                feed.getContent(),
                UserInfo.from(feed.getAuthor()),
                CommunityInfo.from(feed.getCommunity()),
                feed.getComments().stream()
                        .map(CommentInfo::from)
                        .toList()
        );
    }
}