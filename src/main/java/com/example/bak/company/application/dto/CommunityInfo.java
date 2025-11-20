package com.example.bak.company.application.dto;

import com.example.bak.community.domain.Community;

/**
 * Community 도메인의 기본 정보를 담는 DTO 다른 도메인에서 Community 정보가 필요할 때 재사용 가능
 */
public record CommunityInfo(
        Long id,
        String name,
        String jobGroup
) {

    public static CommunityInfo from(Community community) {
        return new CommunityInfo(
                community.getId(),
                community.getName(),
                community.getJobGroup()
        );
    }
}
