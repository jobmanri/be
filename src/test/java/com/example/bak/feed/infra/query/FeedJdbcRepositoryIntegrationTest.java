package com.example.bak.feed.infra.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.feed.application.query.dto.FeedDetail;
import com.example.bak.feed.application.query.dto.FeedSummary;
import com.example.bak.feed.infra.query.jdbc.FeedJdbcRepository;
import com.example.bak.global.AbstractMySqlContainerTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        }
    }

    @Nested
    @DisplayName("findAll")
    class FindAll {

        @Test
        @DisplayName("페이지네이션으로 피드 목록을 조회한다")
        void success() {
            Page<FeedSummary> page = feedJdbcRepository.findAll(PageRequest.of(0, 2));

            assertThat(page.getTotalElements()).isEqualTo(3);
            List<FeedSummary> content = page.getContent();
            assertThat(content).hasSize(2);
            assertThat(content.getFirst().id()).isEqualTo(3L); // DESC order
        }
    }
}
