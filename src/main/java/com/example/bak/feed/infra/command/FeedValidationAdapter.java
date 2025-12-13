package com.example.bak.feed.infra.command;

import com.example.bak.feed.application.command.port.FeedValidationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedValidationAdapter implements FeedValidationPort {

    private final FeedJpaRepository feedJpaRepository;

    @Override
    public boolean existsById(Long feedId) {
        return feedJpaRepository.existsById(feedId);
    }
}
