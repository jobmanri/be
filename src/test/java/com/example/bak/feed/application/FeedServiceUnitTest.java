package com.example.bak.feed.application;

import static com.example.bak.global.utils.AssertionsErrorCode.assertBusiness;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.community.domain.Community;
import com.example.bak.community.domain.CommunityRepositoryStub;
import com.example.bak.company.domain.Company;
import com.example.bak.feed.application.query.dto.FeedDetail;
import com.example.bak.feed.application.query.dto.FeedResult;
import com.example.bak.feed.application.query.dto.FeedSummary;
import com.example.bak.feed.domain.Feed;
import com.example.bak.feed.domain.FeedRepositoryStub;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.user.domain.User;
import com.example.bak.user.domain.UserRepositoryStub;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("FeedService 단위 테스트")
class FeedServiceUnitTest {

    private static final Long EXISTING_USER_ID = 1L;
    private static final Long EXISTING_COMPANY_ID = 5L;
    private static final Long EXISTING_COMMUNITY_ID = 10L;
    private static final Long NOT_FOUND_USER_ID = 999L;
    private static final Long NOT_FOUND_COMMUNITY_ID = 888L;
    private static final Long NOT_FOUND_FEED_ID = 777L;

    private static final String TITLE = "title";
    private static final String CONTENT = "content";

    private final User testUser =
            User.testInstance(EXISTING_USER_ID, "user@test.com", "pw", "name", "nick");

    private final Company company =
            Company.testInstance(EXISTING_COMPANY_ID, "testDotCom", "test.com", "image.url.com",
                    "testing company1");

    private final Community community =
            Community.testInstance(EXISTING_COMMUNITY_ID, "community", "jobGroup", EXISTING_COMPANY_ID);

    private FeedService feedService;
    private FeedRepositoryStub feedRepository;
    private UserRepositoryStub userRepository;
    private CommunityRepositoryStub communityRepository;

    @BeforeEach
    void setUp() {
        feedRepository = new FeedRepositoryStub();
        userRepository = new UserRepositoryStub();
        communityRepository = new CommunityRepositoryStub();

        feedService = new FeedService(feedRepository, userRepository, communityRepository);

        userRepository.save(testUser);
        communityRepository.save(community);
    }

    private List<Feed> createFeeds(int count) {
        return IntStream.rangeClosed(1, count)
                .mapToObj(i -> Feed.testInstance(
                        (long) i,
                        TITLE + i,
                        CONTENT + i,
                        community,
                        testUser
                ))
                .toList();
    }

    @Nested
    @DisplayName("createFeed 테스트")
    class CreateFeedTest {

        @Test
        @DisplayName("피드 생성 성공")
        void createFeed_success() {
            // when
            FeedResult result = feedService.createFeed(
                    TITLE, CONTENT,
                    EXISTING_COMMUNITY_ID,
                    EXISTING_USER_ID
            );

            // then
            assertThat(result).isNotNull();
            var saved = feedRepository.findAll().getFirst();
            assertThat(saved.getTitle()).isEqualTo(TITLE);
            assertThat(saved.getContent()).isEqualTo(CONTENT);
        }

        @Test
        @DisplayName("존재하지 않는 사용자면 예외 발생")
        void createFeed_when_userNotFound() {
            assertBusiness(
                    () -> feedService.createFeed(TITLE, CONTENT, EXISTING_COMMUNITY_ID,
                            NOT_FOUND_USER_ID),
                    ErrorCode.USER_NOT_FOUND
            );
        }

        @Test
        @DisplayName("존재하지 않는 커뮤니티면 예외 발생")
        void createFeed_when_communityNotFound() {
            assertBusiness(
                    () -> feedService.createFeed(TITLE, CONTENT, NOT_FOUND_COMMUNITY_ID,
                            EXISTING_USER_ID),
                    ErrorCode.COMMUNITY_NOT_FOUND
            );
        }
    }

    @Nested
    @DisplayName("getFeedDetail 테스트")
    class GetFeedDetailTest {

        @Test
        @DisplayName("피드 상세 조회 성공")
        void getFeedDetail_success() {
            // given
            Feed saved = feedRepository.save(
                    Feed.create(TITLE, CONTENT, community, testUser)
            );

            // when
            FeedDetail detail = feedService.getFeedDetail(saved.getId());

            // then
            assertThat(detail.id()).isEqualTo(saved.getId());
            assertThat(detail.title()).isEqualTo(TITLE);
        }

        @Test
        @DisplayName("없는 피드 조회 시 예외 발생")
        void getFeedDetail_when_notFound() {
            assertBusiness(
                    () -> feedService.getFeedDetail(NOT_FOUND_FEED_ID),
                    ErrorCode.FEED_NOT_FOUND
            );
        }
    }

    @Nested
    @DisplayName("getFeedSummary 테스트")
    class GetFeedSummaryTest {

        @Test
        @DisplayName("피드 요약 조회 성공")
        void getFeedSummary_success() {
            Feed saved = feedRepository.save(
                    Feed.create(TITLE, CONTENT, community, testUser)
            );

            FeedSummary summary = feedService.getFeedSummary(saved.getId());

            assertThat(summary.id()).isEqualTo(saved.getId());
            assertThat(summary.title()).isEqualTo(TITLE);
        }

        @Test
        @DisplayName("없는 피드 요약 조회 시 예외 발생")
        void getFeedSummary_when_notFound() {
            assertBusiness(
                    () -> feedService.getFeedSummary(NOT_FOUND_FEED_ID),
                    ErrorCode.FEED_NOT_FOUND
            );
        }
    }

    @Nested
    @DisplayName("getFeeds 테스트")
    class GetFeedsTest {

        @Test
        @DisplayName("페이징 조회 성공")
        void getFeeds_success() {
            // given
            createFeeds(5).forEach(feedRepository::save);

            // when
            List<FeedSummary> results = feedService.getFeeds(0, 3);

            // then
            assertThat(results).hasSize(3);
        }
    }
}

