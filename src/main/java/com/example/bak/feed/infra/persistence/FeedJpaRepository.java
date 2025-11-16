package com.example.bak.feed.infra.persistence;

import com.example.bak.feed.domain.Feed;
import com.example.bak.feed.domain.FeedRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedJpaRepository extends JpaRepository<Feed, Long>, FeedRepository {

    @Override
    List<Feed> findAll();

    @Override
    Feed save(Feed feed);
}
