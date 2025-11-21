package com.example.bak.privatemessage.infra.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.global.AbstractMySqlContainerTest;
import com.example.bak.privatemessage.application.query.dto.MessageItemResult;
import com.example.bak.privatemessage.application.query.dto.MessagePartnerResult;
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
public class MessageJdbcRepositoryIntegrationTest extends AbstractMySqlContainerTest {

    @Autowired
    private MessageJdbcRepository messageJdbcRepository;

    @Nested
    @DisplayName("findPartnersByUserId 메소드 쿼리 테스트")
    class FindPartnersByUserIdTest {

        @Test
        @DisplayName("성공 케이스")
        public void test_find_partners_by_userid_success() {
            var result = messageJdbcRepository.findPartnersByUserId(1L);

            assertThat(result).isNotEmpty();
            assertThat(result).containsExactly(
                    new MessagePartnerResult(2L, "u2", true),
                    new MessagePartnerResult(3L, "u3", false)
            );
        }
    }

    @Nested
    @DisplayName("findMessagesBetweenUsers 메소드 쿼리 테스트")
    class FindMessagesBetweenUsers {

        @Test
        @DisplayName("성공 케이스")
        public void test_find_messages_between_users_success() {
            var result = messageJdbcRepository.findMessagesBetweenUsers(1L, 2L);

            assertThat(result).isNotEmpty();
            assertThat(result).containsExactly(
                    new MessageItemResult(3L, 2L, 1L, "message3", null),
                    new MessageItemResult(2L, 1L, 2L, "message2", null),
                    new MessageItemResult(1L, 1L, 2L, "message1", null)
            );
        }
    }
}

