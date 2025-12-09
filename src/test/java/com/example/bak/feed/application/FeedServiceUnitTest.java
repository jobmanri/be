package com.example.bak.feed.application;

import static com.example.bak.global.utils.AssertionsErrorCode.assertBusiness;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.feed.application.command.FeedCommandService;
import com.example.bak.feed.application.command.dto.FeedResult;
import com.example.bak.feed.application.command.port.CommunityValidationPort;
import com.example.bak.feed.domain.Feed;
import com.example.bak.feed.domain.FeedRepositoryStub;
import com.example.bak.global.exception.ErrorCode;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("FeedService 단위 테스트")
class FeedServiceUnitTest {

    private static final Long EXISTING_USER_ID = 1L;
    private static final Long EXISTING_COMMUNITY_ID = 10L;
    private static final Long NOT_FOUND_COMMUNITY_ID = 999L;

    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static class StubCommunityValidationPort implements CommunityValidationPort {

        private final Set<Long> existingCommunityIds = new HashSet<>();

        void registerCommunity(Long communityId) {
            existingCommunityIds.add(communityId);
        }

        void removeCommunity(Long communityId) {
            existingCommunityIds.remove(communityId);
        }

        @Override
        public boolean isCommunityExists(Long communityId) {
            return existingCommunityIds.contains(communityId);
        }
    }

    @Nested
    @DisplayName("FeedCommandService")
    class FeedCommandServiceTest {

        private FeedCommandService feedCommandService;
        private FeedRepositoryStub feedRepository;
        private StubCommunityValidationPort communityValidationPort;

        @BeforeEach
        void setUp() {
            feedRepository = new FeedRepositoryStub();
            communityValidationPort = new StubCommunityValidationPort();
            communityValidationPort.registerCommunity(EXISTING_COMMUNITY_ID);
            feedCommandService = new FeedCommandService(feedRepository, communityValidationPort);
        }

        @Test
        @DisplayName("피드 생성 성공")
        void createFeed_success() {
            FeedResult result = feedCommandService.createFeed(
                    TITLE,
                    CONTENT,
                    EXISTING_COMMUNITY_ID,
                    EXISTING_USER_ID
            );

            assertThat(result).isNotNull();
            Feed saved = feedRepository.findById(result.id()).orElse(null);
            assertThat(saved).isNotNull();
            assertThat(saved.getTitle()).isEqualTo(TITLE);
            assertThat(saved.getContent()).isEqualTo(CONTENT);
            assertThat(saved.getCommunityId()).isEqualTo(EXISTING_COMMUNITY_ID);
            assertThat(saved.getAuthorId()).isEqualTo(EXISTING_USER_ID);
        }

        @Test
        @DisplayName("존재하지 않는 커뮤니티면 예외 발생")
        void createFeed_when_communityNotFound() {
            communityValidationPort.removeCommunity(EXISTING_COMMUNITY_ID);

            assertBusiness(
                    () -> feedCommandService.createFeed(
                            TITLE,
                            CONTENT,
                            NOT_FOUND_COMMUNITY_ID,
                            EXISTING_USER_ID
                    ),
                    ErrorCode.COMMUNITY_NOT_FOUND
            );
        }

        @Test
        @DisplayName("피드를 수정한다")
        void updateFeed_success() {
            feedRepository.save(Feed.testInstance(1L, TITLE, CONTENT, EXISTING_COMMUNITY_ID,
                    EXISTING_USER_ID));

            feedCommandService.updateFeed(1L, "updatedTitle", "updatedContent",
                    EXISTING_USER_ID);

            Feed updated = feedRepository.findById(1L).orElseThrow();
            assertThat(updated.getTitle()).isEqualTo("updatedTitle");
            assertThat(updated.getContent()).isEqualTo("updatedContent");
        }

        @Test
        @DisplayName("피드 수정 시 작성자가 아니면 예외")
        void updateFeed_when_notAuthor() {
            feedRepository.save(Feed.testInstance(1L, TITLE, CONTENT, EXISTING_COMMUNITY_ID,
                    EXISTING_USER_ID));

            assertBusiness(
                    () -> feedCommandService.updateFeed(1L, "updatedTitle", "updatedContent",
                            999L),
                    ErrorCode.UNAUTHORIZED_ACTION
            );
        }

        @Test
        @DisplayName("존재하지 않는 피드 수정 시 예외")
        void updateFeed_when_notFound() {
            assertBusiness(
                    () -> feedCommandService.updateFeed(1L, "title", "content",
                            EXISTING_USER_ID),
                    ErrorCode.FEED_NOT_FOUND
            );
        }

        @Test
        @DisplayName("피드를 삭제한다")
        void deleteFeed_success() {
            feedRepository.save(Feed.testInstance(1L, TITLE, CONTENT, EXISTING_COMMUNITY_ID,
                    EXISTING_USER_ID));

            feedCommandService.deleteFeed(1L, EXISTING_USER_ID);

            assertThat(feedRepository.findById(1L)).isEmpty();
        }

        @Test
        @DisplayName("피드 삭제 시 작성자가 아니면 예외")
        void deleteFeed_when_notAuthor() {
            feedRepository.save(Feed.testInstance(1L, TITLE, CONTENT, EXISTING_COMMUNITY_ID,
                    EXISTING_USER_ID));

            assertBusiness(
                    () -> feedCommandService.deleteFeed(1L, 999L),
                    ErrorCode.UNAUTHORIZED_ACTION
            );
        }

        @Test
        @DisplayName("존재하지 않는 피드 삭제 시 예외")
        void deleteFeed_when_notFound() {
            assertBusiness(
                    () -> feedCommandService.deleteFeed(1L, EXISTING_USER_ID),
                    ErrorCode.FEED_NOT_FOUND
            );
        }
    }

}
