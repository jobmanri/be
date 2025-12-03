package com.example.bak.feed.infra.command;

import com.example.bak.feed.domain.Feed;
import com.example.bak.feed.domain.FeedRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedJpaRepository extends JpaRepository<Feed, Long>, FeedRepository {

    @Override
    List<Feed> findAll();

    @Override
    Page<Feed> findAll(Pageable pageable);

    @Override
    Feed save(Feed feed);

    @Override
    Optional<Feed> findById(Long id);
}
