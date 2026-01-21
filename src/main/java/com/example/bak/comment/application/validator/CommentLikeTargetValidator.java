package com.example.bak.comment.application.validator;

import com.example.bak.comment.application.command.port.CommentValidationPort;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.like.application.command.validator.LikeTargetValidator;
import com.example.bak.like.domain.LikeTargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentLikeTargetValidator implements LikeTargetValidator {

    private final CommentValidationPort commentValidationPort;

    @Override
    public LikeTargetType targetType() {
        return LikeTargetType.COMMENT;
    }

    @Override
    public void validateExists(Long targetId) {
        if (!commentValidationPort.existsById(targetId)) {
            throw new BusinessException(ErrorCode.COMMENT_NOT_FOUND);
        }
    }
}
