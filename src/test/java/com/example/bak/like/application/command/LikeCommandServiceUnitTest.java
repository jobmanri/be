package com.example.bak.like.application.command;

import static com.example.bak.global.utils.AssertionsErrorCode.assertBusiness;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.example.bak.comment.application.command.port.CommentValidationPort;
import com.example.bak.comment.application.validator.CommentLikeTargetValidator;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.like.application.command.port.LikeCommandPort;
import com.example.bak.like.application.command.validator.LikeTargetValidator;
import com.example.bak.like.domain.Like;
import com.example.bak.like.domain.LikeTargetType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("LikeCommandService 단위 테스트")
class LikeCommandServiceUnitTest {

    private static final Long EXISTING_COMMENT_ID = 1L;
    private static final Long NOT_FOUND_COMMENT_ID = 999L;
    private static final Long USER_ID = 10L;

    private LikeCommandService likeCommandService;
    private InMemoryLikeCommandPort likeCommandPort;

    @BeforeEach
    void setUp() {
        CommentValidationPort commentValidationPort = new InMemoryCommentValidationPort(
                Set.of(EXISTING_COMMENT_ID));
        LikeTargetValidator commentValidator = new CommentLikeTargetValidator(commentValidationPort);

        likeCommandPort = new InMemoryLikeCommandPort();
        likeCommandService = new LikeCommandService(likeCommandPort, List.of(commentValidator));
        likeCommandService.initValidatorMap();
    }

    @Nested
    @DisplayName("like")
    class LikeOperation {

        @Test
        @DisplayName("댓글 좋아요에 성공한다")
        void likeComment_success() {
            assertThatCode(() -> likeCommandService.like(LikeTargetType.COMMENT, EXISTING_COMMENT_ID,
                    USER_ID)).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("이미 좋아요를 누른 상태면 예외")
        void likeComment_whenAlreadyExists() {
            likeCommandService.like(LikeTargetType.COMMENT, EXISTING_COMMENT_ID, USER_ID);

            assertBusiness(
                    () -> likeCommandService.like(LikeTargetType.COMMENT, EXISTING_COMMENT_ID, USER_ID),
                    ErrorCode.LIKE_ALREADY_EXISTS
            );
        }

        @Test
        @DisplayName("존재하지 않는 댓글이면 예외")
        void likeComment_whenCommentNotFound() {
            assertBusiness(
                    () -> likeCommandService.like(LikeTargetType.COMMENT, NOT_FOUND_COMMENT_ID, USER_ID),
                    ErrorCode.COMMENT_NOT_FOUND
            );
        }
    }

    @Nested
    @DisplayName("unlike")
    class Unlike {

        @Test
        @DisplayName("좋아요가 없어도 예외 없이 종료한다")
        void unlike_whenNotExists() {
            assertThatCode(() -> likeCommandService.unlike(LikeTargetType.COMMENT, EXISTING_COMMENT_ID,
                    USER_ID)).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("존재하지 않는 댓글이면 예외")
        void unlike_whenCommentNotFound() {
            assertBusiness(
                    () -> likeCommandService.unlike(LikeTargetType.COMMENT, NOT_FOUND_COMMENT_ID, USER_ID),
                    ErrorCode.COMMENT_NOT_FOUND
            );
        }
    }

    private static final class InMemoryCommentValidationPort implements CommentValidationPort {

        private final Set<Long> existing;

        private InMemoryCommentValidationPort(Set<Long> existing) {
            this.existing = existing;
        }

        @Override
        public boolean existsById(Long commentId) {
            return existing.contains(commentId);
        }
    }

    private static final class InMemoryLikeCommandPort implements LikeCommandPort {

        private final Set<String> keys = new HashSet<>();

        @Override
        public com.example.bak.like.domain.Like save(com.example.bak.like.domain.Like like) {
            keys.add(key(like.getTargetType(), like.getTargetId(), like.getUserId()));
            return like;
        }

        @Override
        public boolean exists(LikeTargetType targetType, Long targetId, Long userId) {
            return keys.contains(key(targetType, targetId, userId));
        }

        @Override
        public void delete(LikeTargetType targetType, Long targetId, Long userId) {
            keys.remove(key(targetType, targetId, userId));
        }

        private String key(LikeTargetType targetType, Long targetId, Long userId) {
            return targetType + ":" + targetId + ":" + userId;
        }
    }
}
