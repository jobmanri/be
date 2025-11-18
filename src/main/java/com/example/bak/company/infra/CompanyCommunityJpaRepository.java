package com.example.bak.company.infra;

import com.example.bak.company.domain.CompanyCommunity;
import com.example.bak.company.domain.CompanyCommunityRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyCommunityJpaRepository extends JpaRepository<CompanyCommunity, Long>, CompanyCommunityRepository {
}
