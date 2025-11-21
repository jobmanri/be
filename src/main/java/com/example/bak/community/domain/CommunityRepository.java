package com.example.bak.community.domain;

import java.util.List;
import java.util.Optional;

public interface CommunityRepository {

    Community save(Community companyCommunity);

    Optional<Community> findById(Long id);

    List<Community> findByCompanyId(Long id);

    void deleteById(Long id);
}
