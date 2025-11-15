package com.example.bak.feed.domain;

import java.util.List;

public interface FeedRepository {

    List<Feed> findAll();

    Feed save(Feed feed);
}
