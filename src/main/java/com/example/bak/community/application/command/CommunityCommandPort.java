package com.example.bak.community.application.command;

import com.example.bak.community.domain.Community;
import java.util.Optional;

public interface CommunityCommandPort {

    Community save(Community community);

    Optional<Community> findById(Long id);

    void deleteById(Long id);
}
