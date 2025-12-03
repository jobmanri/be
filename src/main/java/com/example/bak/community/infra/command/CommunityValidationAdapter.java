package com.example.bak.community.infra.command;

import com.example.bak.feed.application.command.port.CommunityValidationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityValidationAdapter implements CommunityValidationPort {

    private final CommunityJpaRepository communityJpaRepository;

    @Override
    public boolean isCommunityExists(Long communityId) {
        return communityJpaRepository.existsById(communityId);
    }
}
