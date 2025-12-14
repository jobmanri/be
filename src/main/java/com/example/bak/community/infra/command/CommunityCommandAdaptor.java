package com.example.bak.community.infra.command;

import com.example.bak.community.application.command.port.CommunityCommandPort;
import com.example.bak.community.domain.Community;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityCommandAdaptor implements CommunityCommandPort {

    private final CommunityJpaRepository communityJpaRepository;

    @Override
    public Community save(Community community) {
        return communityJpaRepository.save(community);
    }

    @Override
    public Optional<Community> findById(Long id) {
        return communityJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        communityJpaRepository.deleteById(id);
    }
}
