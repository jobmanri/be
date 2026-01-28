package com.example.bak.like.infra.command;

import com.example.bak.like.application.command.port.LikeCommandPort;
import com.example.bak.like.domain.Like;
import com.example.bak.like.domain.LikeTargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeCommandAdapter implements LikeCommandPort {

    private final LikeJpaRepository likeJpaRepository;

    @Override
    public Like save(Like like) {
        return likeJpaRepository.save(like);
    }

    @Override
    public boolean exists(LikeTargetType targetType, Long targetId, Long userId) {
        return likeJpaRepository.existsByTargetTypeAndTargetIdAndUserId(targetType, targetId, userId);
    }

    @Override
    public void delete(LikeTargetType targetType, Long targetId, Long userId) {
        likeJpaRepository.deleteByTargetTypeAndTargetIdAndUserId(targetType, targetId, userId);
    }
}
