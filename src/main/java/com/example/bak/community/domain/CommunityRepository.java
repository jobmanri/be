package com.example.bak.community.domain;

import java.util.Optional;

public interface CommunityRepository {

    Community save(Community companyCommunity);

    Optional<Community> findById(Long id);

    void deleteById(Long id);
}
