package com.example.bak.community.infra.command;

import com.example.bak.community.domain.Community;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityJpaRepository extends JpaRepository<Community, Long> {

    Community save(Community companyCommunity);

    Optional<Community> findById(Long id);

    void deleteById(Long id);
}
