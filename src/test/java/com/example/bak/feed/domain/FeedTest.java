package com.example.bak.feed.domain;

import static com.example.bak.global.utils.AssertionsErrorCode.assertBusiness;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.example.bak.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Feed 도메인 테스트")
class FeedTest {

    @Test
    @DisplayName("작성자 검증에 성공한다")
    void validateAuthor_success() {
        Feed feed = Feed.testInstance(1L, "title", "content", 1L, 10L);

        assertThatCode(() -> feed.validateAuthor(10L)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("작성자 검증 실패 시 예외")
    void validateAuthor_when_unauthorized() {
        Feed feed = Feed.testInstance(1L, "title", "content", 1L, 10L);

        assertBusiness(() -> feed.validateAuthor(99L), ErrorCode.UNAUTHORIZED_ACTION);
    }
}
