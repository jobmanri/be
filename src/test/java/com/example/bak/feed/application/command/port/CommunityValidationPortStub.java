package com.example.bak.feed.application.command.port;

import java.util.HashSet;
import java.util.Set;

public class CommunityValidationPortStub implements CommunityValidationPort {

    private final Set<Long> existingCommunityIds = new HashSet<>();

    public void registerCommunity(Long communityId) {
        existingCommunityIds.add(communityId);
    }

    public void removeCommunity(Long communityId) {
        existingCommunityIds.remove(communityId);
    }

    @Override
    public boolean isCommunityExists(Long communityId) {
        return existingCommunityIds.contains(communityId);
    }
}
