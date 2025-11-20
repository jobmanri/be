package com.example.bak.community.infra;

import com.example.bak.community.domain.Community;
import com.example.bak.community.domain.CommunityRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityJpaRepository extends JpaRepository<Community, Long>,
        CommunityRepository {

}
