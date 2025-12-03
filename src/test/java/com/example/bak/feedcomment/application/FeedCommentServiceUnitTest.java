package com.example.bak.feedcomment.application;

import static com.example.bak.global.utils.AssertionsErrorCode.assertBusiness;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.community.domain.Community;
import com.example.bak.company.domain.Company;
import com.example.bak.feed.domain.Feed;
import com.example.bak.feed.domain.FeedRepositoryStub;
import com.example.bak.feedcomment.domain.FeedCommentRepositoryStub;
import com.example.bak.feedcomment.domain.FeedComment;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.user.domain.User;
import com.example.bak.user.domain.UserRepositoryStub;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("FeedCommentService 단위 테스트")
public class FeedCommentServiceUnitTest {

    private static final Long EXISTING_USER_ID = 1L;
    private static final Long EXISTING_FEED_ID = 1L;

    private static final Long NOT_FOUND_USER_ID = 999L;
    private static final Long NOT_FOUND_FEED_ID = 999L;

    private static final String COMMENT_CONTENT = "content";
    private static final String UPDATED_CONTENT = "updatedContent";

    private final User testUser =
            User.testInstance(EXISTING_USER_ID, "test@test.com", "password", "name", "nickname");

    private final Company company =
            Company.testInstance(1L, "testDotCom", "test.com", "image.url.com",
                    "testing company1");

    private final Community community =
            Community.testInstance(1L, "name", "jobGroup", 1L);

    private final Feed testFeed =
            Feed.testInstance(
                    EXISTING_FEED_ID,
                    "title",
                    COMMENT_CONTENT,
                    community.getId(),
                    testUser.getId()
            );

    private FeedCommentService feedCommentService;
    private FeedCommentRepositoryStub feedCommentRepository;

    @BeforeEach
    void setUp() {
        FeedRepositoryStub feedRepository = new FeedRepositoryStub();
        UserRepositoryStub userRepository = new UserRepositoryStub();

        feedCommentRepository = new FeedCommentRepositoryStub();

        feedCommentService = new FeedCommentService(
                feedCommentRepository,
                feedRepository,
                userRepository
        );

        userRepository.save(testUser);
        feedRepository.save(testFeed);
    }

    private List<FeedComment> createComments(int count) {
        return IntStream.rangeClosed(1, count)
                .mapToObj(i -> FeedComment.testInstance(
                        (long) i,
                        COMMENT_CONTENT,
                        testUser,
                        testFeed
                ))
                .toList();
    }

    @Nested
    @DisplayName("createComment 테스트")
    class CreateCommentTest {

        @Test
        @DisplayName("피드 댓글 생성에 성공한다")
        void createComment_success() {
            // when
            feedCommentService.createComment(EXISTING_FEED_ID, COMMENT_CONTENT, EXISTING_USER_ID);

            // then
            var saved = feedCommentRepository.findAll().getFirst();
            assertThat(saved.getAuthor().getId()).isEqualTo(EXISTING_USER_ID);
            assertThat(saved.getFeed().getId()).isEqualTo(EXISTING_FEED_ID);
            assertThat(saved.getComment()).isEqualTo(COMMENT_CONTENT);
        }

        @Test
        @DisplayName("존재하지 않는 피드에 예외를 던진다")
        void createComment_when_feedNotFound() {
            assertBusiness(
                    () -> feedCommentService.createComment(
                            NOT_FOUND_FEED_ID,
                            COMMENT_CONTENT,
                            EXISTING_USER_ID
                    ),
                    ErrorCode.FEED_NOT_FOUND
            );
        }

        @Test
        @DisplayName("존재하지 않는 사용자에 예외를 던진다")
        void createComment_when_userNotFound() {
            assertBusiness(
                    () -> feedCommentService.createComment(
                            EXISTING_FEED_ID,
                            COMMENT_CONTENT,
                            NOT_FOUND_USER_ID
                    ),
                    ErrorCode.USER_NOT_FOUND
            );
        }
    }

    @Nested
    @DisplayName("updateComment 테스트")
    class UpdateCommentTest {

        @Test
        @DisplayName("피드 댓글 업데이트에 성공한다")
        void updateComment_success() {
            // given
            feedCommentRepository.save(
                    FeedComment.testInstance(1L, COMMENT_CONTENT, testUser, testFeed)
            );

            // when
            feedCommentService.updateComment(1L, UPDATED_CONTENT, EXISTING_USER_ID);

            // then
            var updated = feedCommentRepository.findById(1L);
            assertThat(updated).isPresent();
            assertThat(updated.get().getComment()).isEqualTo(UPDATED_CONTENT);
        }

        @Test
        @DisplayName("피드 댓글 업데이트 권한이 없을 때 예외를 던진다")
        void updateComment_when_isNotAuthor() {
            feedCommentRepository.save(
                    FeedComment.testInstance(1L, COMMENT_CONTENT, testUser, testFeed)
            );

            assertBusiness(
                    () -> feedCommentService.updateComment(1L, UPDATED_CONTENT, NOT_FOUND_USER_ID),
                    ErrorCode.UNAUTHORIZED_ACTION
            );
        }

        @Test
        @DisplayName("존해하지 피드 댓글에 예외를 던진다.")
        void updateComment_when_feedNotFound() {
            assertBusiness(
                    () -> feedCommentService.updateComment(1L, UPDATED_CONTENT, EXISTING_USER_ID),
                    ErrorCode.COMMENT_NOT_FOUND
            );
        }
    }

    @Nested
    @DisplayName("getComments 테스트")
    class GetCommentsTest {

        @Test
        void getComments_success() {
            // given
            createComments(4).forEach(feedCommentRepository::save);

            // when
            var comments = feedCommentService.getComments(EXISTING_FEED_ID);

            // then
            assertThat(comments).hasSize(4);
        }
    }
}
