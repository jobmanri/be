package com.example.bak.feed.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedRepository {

    List<Feed> findAll();

    Page<Feed> findAll(Pageable pageable);

    Feed save(Feed feed);

    Optional<Feed> findFeedById(Long id);
}
