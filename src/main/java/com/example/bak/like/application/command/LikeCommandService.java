package com.example.bak.like.application.command;

import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.like.application.command.port.LikeCommandPort;
import com.example.bak.like.application.command.validator.LikeTargetValidator;
import com.example.bak.like.domain.Like;
import com.example.bak.like.domain.LikeTargetType;
import jakarta.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeCommandService {

    private final LikeCommandPort likeCommandPort;
    private final List<LikeTargetValidator> validators;
    private Map<LikeTargetType, LikeTargetValidator> validatorMap;

    @PostConstruct
    void initValidatorMap() {
        validatorMap = new EnumMap<>(LikeTargetType.class);
        for (LikeTargetValidator validator : validators) {
            validatorMap.put(validator.targetType(), validator);
        }
    }

    public void like(LikeTargetType targetType, Long targetId, Long userId) {
        LikeTargetValidator validator = getValidator(targetType);
        validator.validateExists(targetId);

        if (likeCommandPort.exists(targetType, targetId, userId)) {
            return;
        }

        likeCommandPort.save(Like.create(targetType, targetId, userId));
    }

    public void unlike(LikeTargetType targetType, Long targetId, Long userId) {
        LikeTargetValidator validator = getValidator(targetType);
        validator.validateExists(targetId);

        if (!likeCommandPort.exists(targetType, targetId, userId)) {
            return;
        }

        likeCommandPort.delete(targetType, targetId, userId);
    }

    private LikeTargetValidator getValidator(LikeTargetType targetType) {
        LikeTargetValidator validator = validatorMap.get(targetType);
        if (validator == null) {
            throw new BusinessException(ErrorCode.INVALID_LIKE_TARGET);
        }
        return validator;
    }
}
