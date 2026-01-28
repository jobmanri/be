package com.example.bak.feed.application.validator;

import com.example.bak.feed.application.command.port.FeedValidationPort;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.like.application.command.validator.LikeTargetValidator;
import com.example.bak.like.domain.LikeTargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedLikeTargetValidator implements LikeTargetValidator {

    private final FeedValidationPort feedValidationPort;

    @Override
    public LikeTargetType targetType() {
        return LikeTargetType.FEED;
    }

    @Override
    public void validateExists(Long targetId) {
        if (!feedValidationPort.existsById(targetId)) {
            throw new BusinessException(ErrorCode.FEED_NOT_FOUND);
        }
    }
}
