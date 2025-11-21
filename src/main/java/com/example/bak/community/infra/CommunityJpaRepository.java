package com.example.bak.community.infra;

import com.example.bak.community.domain.Community;
import com.example.bak.community.domain.CommunityRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityJpaRepository extends JpaRepository<Community, Long>,
        CommunityRepository {

    @Override
    Community save(Community companyCommunity);

    @Override
    Optional<Community> findById(Long id);

    @Override
    List<Community> findByCompanyId(Long id);

    @Override
    void deleteById(Long id);
}
