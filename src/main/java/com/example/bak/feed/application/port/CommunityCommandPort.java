package com.example.bak.feed.application.port;

import com.example.bak.community.domain.Community;
import java.util.Optional;

public interface CommunityCommandPort {

    Optional<Community> findById(Long communityId);
}
