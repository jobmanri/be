package com.example.bak.feed.infra.command;

import com.example.bak.feed.domain.Feed;
import com.example.bak.feed.domain.FeedRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedJpaRepository extends JpaRepository<Feed, Long>, FeedRepository {

    @Override
    Feed save(Feed feed);

}
