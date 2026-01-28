package com.example.bak.like.application.command.port;

import com.example.bak.like.domain.Like;
import com.example.bak.like.domain.LikeTargetType;

public interface LikeCommandPort {

    Like save(Like like);

    boolean exists(LikeTargetType targetType, Long targetId, Long userId);

    void delete(LikeTargetType targetType, Long targetId, Long userId);
}
