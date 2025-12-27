package com.example.bak.feed.infra.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.feed.application.query.dto.FeedDetail;
import com.example.bak.feed.application.query.dto.FeedSummary;
import com.example.bak.feed.application.query.dto.FeedTitleOnly;
import com.example.bak.feed.infra.query.jdbc.FeedJdbcRepository;
import com.example.bak.global.AbstractMySqlContainerTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@DisplayName("FeedJdbcRepository 통합 테스트")
@Sql(scripts = "/sql/feed/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class FeedJdbcRepositoryIntegrationTest extends AbstractMySqlContainerTest {

    @Autowired
    private FeedJdbcRepository feedJdbcRepository;

    @Nested
    @DisplayName("findDetailById")
    class FindDetailById {

        @Test
        @DisplayName("피드 상세 정보를 조회한다")
        void success() {
            FeedDetail detail = feedJdbcRepository.findDetailById(1L).orElseThrow();

            assertThat(detail.id()).isEqualTo(1L);
            assertThat(detail.title()).isEqualTo("title1");
            assertThat(detail.author().nickname()).isEqualTo("nick1");
            assertThat(detail.community().name()).isEqualTo("backend");
            assertThat(detail.commentCount()).isEqualTo(2);
            assertThat(detail.likeCount()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("findSummaryById")
    class FindSummaryById {

        @Test
        @DisplayName("피드 요약 정보를 조회하고 댓글 수를 포함한다")
        void success() {
            FeedSummary summary = feedJdbcRepository.findSummaryById(1L).orElseThrow();

            assertThat(summary.id()).isEqualTo(1L);
            assertThat(summary.commentCount()).isEqualTo(2);
            assertThat(summary.author().nickname()).isEqualTo("nick1");
            assertThat(summary.contentPreview()).isEqualTo("content1");
            assertThat(summary.likeCount()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("findRecentSummaries")
    class FindRecentSummaries {

        @Test
        @DisplayName("최신 순으로 지정한 개수만큼 피드 요약을 조회한다")
        void success() {
            List<FeedSummary> summaries = feedJdbcRepository.findRecentSummaries(2);

            assertThat(summaries).hasSize(2);
            assertThat(summaries.getFirst().id()).isEqualTo(3L);
            assertThat(summaries.get(1).id()).isEqualTo(2L);
        }
    }

    @Nested
    @DisplayName("findTitleOnlyByCommunity")
    class FindTitleOnlyByCommunity {

        @Test
        @DisplayName("커뮤니티별 최신 피드 제목을 제한 개수만큼 조회한다")
        void success() {
            List<FeedTitleOnly> titles = feedJdbcRepository.findTitleOnlyByCommunity(1L, 1);

            assertThat(titles).hasSize(1);
            FeedTitleOnly titleOnly = titles.getFirst();
            assertThat(titleOnly.title()).isEqualTo("title2");
            assertThat(titleOnly.likeCount()).isZero();
            assertThat(titleOnly.commentCount()).isEqualTo(1);
            assertThat(titleOnly.community().id()).isEqualTo(1L);
        }
    }

    @Nested
    @DisplayName("findTopDetails")
    class FindTopDetails {

        @Test
        @DisplayName("최신 피드 상세를 개수 제한으로 조회한다")
        void success() {
            List<FeedDetail> details = feedJdbcRepository.findTopDetails(2);

            assertThat(details).hasSize(2);
            assertThat(details.get(0).id()).isEqualTo(3L);
            assertThat(details.get(1).id()).isEqualTo(2L);
        }
    }

    @Nested
    @DisplayName("findByCommunityIdWithCursor")
    class FindByCommunityIdWithCursor {

        @Test
        @DisplayName("커멘티별 피드를 커서 기반으로 조회한다")
        void success() {
            List<FeedSummary> firstPage = feedJdbcRepository.findByCommunityIdWithCursor(1L, null, 1);

            assertThat(firstPage).hasSize(1);
            Long nextCursor = firstPage.getFirst().id();

            List<FeedSummary> secondPage = feedJdbcRepository.findByCommunityIdWithCursor(1L, nextCursor, 1);
            assertThat(secondPage).hasSize(1);
            assertThat(secondPage.getFirst().id()).isLessThan(nextCursor);
        }
    }

    @Nested
    @DisplayName("findTitleOnlyGroupedByCommunity")
    class FindTitleOnlyGroupedByCommunity {

        @Test
        @DisplayName("커뮤니티별로 제한 개수만큼 제목을 그룹핑해 조회한다")
        void success() {
            List<FeedTitleOnly> titles = feedJdbcRepository.findTitleOnlyGroupedByCommunity(1);

            assertThat(titles)
                    .hasSize(2)
                    .extracting(title -> title.community().id())
                    .containsExactlyInAnyOrder(1L, 2L);
        }
    }
}
