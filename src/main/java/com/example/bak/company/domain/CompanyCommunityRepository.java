package com.example.bak.company.domain;

import java.util.Optional;

public interface CompanyCommunityRepository {

    CompanyCommunity save(CompanyCommunity companyCommunity);

    Optional<CompanyCommunity> findById(Long id);
}
