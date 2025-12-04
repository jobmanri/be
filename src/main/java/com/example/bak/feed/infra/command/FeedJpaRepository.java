package com.example.bak.feed.infra.command;

import com.example.bak.feed.domain.Feed;
import com.example.bak.feed.domain.FeedRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedJpaRepository extends JpaRepository<Feed, Long>, FeedRepository {

    @Override
    Feed save(Feed feed);

    @Override
    Optional<Feed> findById(Long id);

}
