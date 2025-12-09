package com.example.bak.comment.application;

import static com.example.bak.global.utils.AssertionsErrorCode.assertBusiness;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.comment.application.command.CommentCommandService;
import com.example.bak.comment.application.command.port.UserDataPort;
import com.example.bak.comment.application.command.port.dto.UserSnapShot;
import com.example.bak.comment.application.query.CommentQueryService;
import com.example.bak.comment.domain.Comment;
import com.example.bak.comment.domain.CommentRepositoryStub;
import com.example.bak.community.domain.Community;
import com.example.bak.company.domain.Company;
import com.example.bak.feed.domain.Feed;
import com.example.bak.feed.domain.FeedRepositoryStub;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.user.domain.User;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("CommentService 단위 테스트")
class CommentServiceUnitTest {

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

    private String nickname() {
        return testUser.getProfile().getNickname();
    }

    private List<Comment> createComments(int count) {
        return IntStream.rangeClosed(1, count)
                .mapToObj(i -> Comment.testInstance(
                        (long) i,
                        testFeed.getId(),
                        COMMENT_CONTENT + i,
                        testUser.getId(),
                        nickname()
                ))
                .toList();
    }

    private static class StubUserDataPort implements UserDataPort {

        private final ConcurrentHashMap<Long, UserSnapShot> store = new ConcurrentHashMap<>();

        void save(User user) {
            store.put(user.getId(),
                    new UserSnapShot(user.getId(), user.getProfile().getNickname()));
        }

        @Override
        public Optional<UserSnapShot> findById(Long userId) {
            return Optional.ofNullable(store.get(userId));
        }
    }

    @Nested
    @DisplayName("CommentCommandService")
    class CommentCommandServiceTest {

        private CommentCommandService commentCommandService;
        private CommentRepositoryStub commentRepository;
        private StubUserDataPort userDataPort;

        @BeforeEach
        void setUp() {
            FeedRepositoryStub feedRepository = new FeedRepositoryStub();
            feedRepository.save(testFeed);

            userDataPort = new StubUserDataPort();
            userDataPort.save(testUser);

            commentRepository = new CommentRepositoryStub();

            commentCommandService = new CommentCommandService(
                    commentRepository,
                    feedRepository,
                    userDataPort
            );
        }

        @Test
        @DisplayName("피드 댓글 생성에 성공한다")
        void createComment_success() {
            commentCommandService.createComment(EXISTING_FEED_ID, COMMENT_CONTENT,
                    EXISTING_USER_ID);

            var saved = commentRepository.findAll().getFirst();
            assertThat(saved.getAuthorId()).isEqualTo(EXISTING_USER_ID);
            assertThat(saved.getAuthorNickname()).isEqualTo(nickname());
            assertThat(saved.getFeedId()).isEqualTo(EXISTING_FEED_ID);
            assertThat(saved.getContent()).isEqualTo(COMMENT_CONTENT);
        }

        @Test
        @DisplayName("존재하지 않는 피드에 예외를 던진다")
        void createComment_when_feedNotFound() {
            assertBusiness(
                    () -> commentCommandService.createComment(
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
                    () -> commentCommandService.createComment(
                            EXISTING_FEED_ID,
                            COMMENT_CONTENT,
                            NOT_FOUND_USER_ID
                    ),
                    ErrorCode.USER_NOT_FOUND
            );
        }

        @Test
        @DisplayName("피드 댓글 업데이트에 성공한다")
        void updateComment_success() {
            commentRepository.save(
                    Comment.testInstance(1L, testFeed.getId(), COMMENT_CONTENT,
                            EXISTING_USER_ID, nickname())
            );

            commentCommandService.updateComment(1L, UPDATED_CONTENT, EXISTING_USER_ID);

            var updated = commentRepository.findById(1L);
            assertThat(updated).isPresent();
            assertThat(updated.get().getContent()).isEqualTo(UPDATED_CONTENT);
        }

        @Test
        @DisplayName("피드 댓글 업데이트 권한이 없을 때 예외를 던진다")
        void updateComment_when_isNotAuthor() {
            commentRepository.save(
                    Comment.testInstance(1L, testFeed.getId(), COMMENT_CONTENT,
                            EXISTING_USER_ID, nickname())
            );

            assertBusiness(
                    () -> commentCommandService.updateComment(1L, UPDATED_CONTENT,
                            NOT_FOUND_USER_ID),
                    ErrorCode.UNAUTHORIZED_ACTION
            );
        }

        @Test
        @DisplayName("존재하지 않는 댓글 업데이트 시 예외")
        void updateComment_when_commentNotFound() {
            assertBusiness(
                    () -> commentCommandService.updateComment(1L, UPDATED_CONTENT,
                            EXISTING_USER_ID),
                    ErrorCode.COMMENT_NOT_FOUND
            );
        }
    }

    @Nested
    @DisplayName("CommentQueryService")
    class CommentQueryServiceTest {

        private CommentQueryService commentQueryService;
        private CommentRepositoryStub commentRepository;

        @BeforeEach
        void setUp() {
            commentRepository = new CommentRepositoryStub();
            commentQueryService = new CommentQueryService(commentRepository);
        }

        @Test
        @DisplayName("댓글 목록 조회 성공")
        void getComments_success() {
            createComments(4).forEach(commentRepository::save);

            var comments = commentQueryService.getComments(EXISTING_FEED_ID);

            assertThat(comments).hasSize(4);
            assertThat(comments.getFirst().authorId()).isEqualTo(EXISTING_USER_ID);
        }
    }
}
