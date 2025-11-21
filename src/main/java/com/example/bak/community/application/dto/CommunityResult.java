package com.example.bak.community.application.dto;

import com.example.bak.community.domain.Community;

public class CommunityResult {

    public record CommunityId(Long value) {

        public static CommunityId from(Community community) {
            return new CommunityId(community.getId());
        }
    }

    public record Detail(
            Long id,
            String name,
            String jobGroup
    ) {

        public static Detail from(Community community) {
            return new Detail(
                    community.getId(), community.getName(), community.getJobGroup()
            );
        }
    }
}
