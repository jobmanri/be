package com.example.bak.privatemessage.infra.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.global.AbstractMySqlContainerTest;
import com.example.bak.global.utils.TestDateTime;
import com.example.bak.privatemessage.application.query.dto.MessageCorrespondentResult;
import com.example.bak.privatemessage.application.query.dto.MessageItemResult;
import com.example.bak.privatemessage.infra.query.jdbc.MessageJdbcRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

@Slf4j
@JdbcTest
@DisplayName("MessageJdbcRepository 통합 테스트")
@Sql(scripts = "/sql/message/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class MessageJdbcRepositoryIntegrationTest extends AbstractMySqlContainerTest {

    private static final String FIXED_TIME = "2025-01-01 10:10:10";
    
    @Autowired
    private MessageJdbcRepository messageJdbcRepository;

    private static MessageItemResult item(
            long id,
            long senderId,
            long receiverId,
            String content
    ) {
        return new MessageItemResult(
                id,
                senderId,
                receiverId,
                content,
                TestDateTime.parse(FIXED_TIME),
                null
        );
    }

    @Nested
    @DisplayName("findCorrespondentsByUserId")
    class FindCorrespondentsByUserIdTest {

        @Test
        @DisplayName("성공적으로 상대방 목록을 조회한다")
        void success() {

            var result = messageJdbcRepository.findCorrespondentsByUserId(1L);

            assertThat(result).containsExactly(
                    new MessageCorrespondentResult(2L, "u2", true),
                    new MessageCorrespondentResult(3L, "u3", false)
            );
        }
    }

    @Nested
    @DisplayName("findMessagesBetweenUsers")
    class FindMessagesBetweenUsersTest {

        @Test
        @DisplayName("두 사용자 간의 메시지 목록을 조회한다")
        void success() {

            var result = messageJdbcRepository.findMessagesBetweenUsers(1L, 2L);

            assertThat(result).containsExactly(
                    item(3L, 2L, 1L, "message3"),
                    item(2L, 1L, 2L, "message2"),
                    item(1L, 1L, 2L, "message1")
            );
        }
    }
}
