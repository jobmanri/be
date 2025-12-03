package com.example.bak.feed.application;

import static com.example.bak.global.utils.AssertionsErrorCode.assertBusiness;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.community.application.query.dto.CommunityResult;
import com.example.bak.feed.application.command.FeedCommandService;
import com.example.bak.feed.application.command.port.CommunityValidationPort;
import com.example.bak.feed.application.command.port.FeedCommandPort;
import com.example.bak.feed.application.query.FeedQueryService;
import com.example.bak.feed.application.query.dto.FeedDetail;
import com.example.bak.feed.application.query.dto.FeedResult;
import com.example.bak.feed.application.query.dto.FeedSummary;
import com.example.bak.feed.application.query.port.FeedQueryPort;
import com.example.bak.feed.domain.Feed;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.user.application.dto.UserInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@DisplayName("FeedService 단위 테스트")
class FeedServiceUnitTest {

    private static final Long EXISTING_USER_ID = 1L;
    private static final Long EXISTING_COMMUNITY_ID = 10L;
    private static final Long NOT_FOUND_COMMUNITY_ID = 999L;

    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String NICKNAME = "nick";
    private static final String COMMUNITY_NAME = "community";
    private static final String JOB_GROUP = "jobGroup";

    @Nested
    @DisplayName("FeedCommandService")
    class FeedCommandServiceTest {

        private FeedCommandService feedCommandService;
        private InMemoryFeedCommandPort feedCommandPort;
        private StubCommunityValidationPort communityValidationPort;

        @BeforeEach
        void setUp() {
            feedCommandPort = new InMemoryFeedCommandPort();
            communityValidationPort = new StubCommunityValidationPort();
            communityValidationPort.registerCommunity(EXISTING_COMMUNITY_ID);
            feedCommandService = new FeedCommandService(feedCommandPort, communityValidationPort);
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
            Feed saved = feedCommandPort.findById(result.id());
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
    }

    @Nested
    @DisplayName("FeedQueryService")
    class FeedQueryServiceTest {

        private FeedQueryService feedQueryService;
        private StubFeedQueryPort feedQueryPort;

        @BeforeEach
        void setUp() {
            feedQueryPort = new StubFeedQueryPort();
            feedQueryService = new FeedQueryService(feedQueryPort);
        }

        @Test
        @DisplayName("피드 상세 조회 성공")
        void getFeedDetail_success() {
            feedQueryPort.save(summaryOf(1L, 0), detailOf(1L));

            FeedDetail detail = feedQueryService.getFeedDetail(1L);

            assertThat(detail.id()).isEqualTo(1L);
            assertThat(detail.title()).isEqualTo(TITLE + 1);
        }

        @Test
        @DisplayName("피드 상세 조회 시 예외")
        void getFeedDetail_when_notFound() {
            assertBusiness(
                    () -> feedQueryService.getFeedDetail(1L),
                    ErrorCode.FEED_NOT_FOUND
            );
        }

        @Test
        @DisplayName("피드 요약 조회 성공")
        void getFeedSummary_success() {
            feedQueryPort.save(summaryOf(2L, 3), detailOf(2L));

            FeedSummary summary = feedQueryService.getFeedSummary(2L);

            assertThat(summary.id()).isEqualTo(2L);
            assertThat(summary.commentCount()).isEqualTo(3);
        }

        @Test
        @DisplayName("피드 요약 조회 시 예외")
        void getFeedSummary_when_notFound() {
            assertBusiness(
                    () -> feedQueryService.getFeedSummary(2L),
                    ErrorCode.FEED_NOT_FOUND
            );
        }

        @Test
        @DisplayName("피드 목록 페이징 조회")
        void getFeeds_success() {
            for (long i = 1; i <= 5; i++) {
                feedQueryPort.save(summaryOf(i, (int) i), detailOf(i));
            }

            List<FeedSummary> feeds = feedQueryService.getFeeds(0, 3);

            assertThat(feeds).hasSize(3);
            assertThat(feeds.get(0).id()).isEqualTo(1L);
        }
    }

    private FeedSummary summaryOf(Long id, int commentCount) {
        return new FeedSummary(
                id,
                TITLE + id,
                authorInfo(),
                communityDetail(),
                commentCount
        );
    }

    private FeedDetail detailOf(Long id) {
        return new FeedDetail(
                id,
                TITLE + id,
                CONTENT + id,
                authorInfo(),
                communityDetail()
        );
    }

    private UserInfo authorInfo() {
        return new UserInfo(EXISTING_USER_ID, NICKNAME);
    }

    private CommunityResult.Detail communityDetail() {
        return new CommunityResult.Detail(EXISTING_COMMUNITY_ID, COMMUNITY_NAME, JOB_GROUP);
    }

    private static class InMemoryFeedCommandPort implements FeedCommandPort {

        private final Map<Long, Feed> store = new HashMap<>();
        private long sequence = 1L;

        @Override
        public Feed save(Feed feed) {
            Long id = feed.getId();
            if (id == null) {
                id = sequence++;
            }

            Feed persisted = Feed.testInstance(
                    id,
                    feed.getTitle(),
                    feed.getContent(),
                    feed.getCommunityId(),
                    feed.getAuthorId()
            );
            store.put(id, persisted);
            return persisted;
        }

        Feed findById(Long id) {
            return store.get(id);
        }
    }

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

    private static class StubFeedQueryPort implements FeedQueryPort {

        private final Map<Long, FeedSummary> summaries = new LinkedHashMap<>();
        private final Map<Long, FeedDetail> details = new LinkedHashMap<>();

        void save(FeedSummary summary, FeedDetail detail) {
            summaries.put(summary.id(), summary);
            details.put(detail.id(), detail);
        }

        @Override
        public Page<FeedSummary> findAll(Pageable pageable) {
            List<FeedSummary> list = new ArrayList<>(summaries.values());
            int start = (int) pageable.getOffset();
            if (start >= list.size()) {
                return new PageImpl<>(List.of(), pageable, list.size());
            }

            int end = Math.min(start + pageable.getPageSize(), list.size());
            return new PageImpl<>(list.subList(start, end), pageable, list.size());
        }

        @Override
        public Optional<FeedSummary> findSummaryById(Long feedId) {
            return Optional.ofNullable(summaries.get(feedId));
        }

        @Override
        public Optional<FeedDetail> findDetailById(Long feedId) {
            return Optional.ofNullable(details.get(feedId));
        }
    }
}

