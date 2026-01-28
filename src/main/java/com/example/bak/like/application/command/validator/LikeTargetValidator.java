package com.example.bak.like.application.command.validator;

import com.example.bak.like.domain.LikeTargetType;

public interface LikeTargetValidator {

    LikeTargetType targetType();

    void validateExists(Long targetId);
}
