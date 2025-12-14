package com.example.bak.user.infra.persistence.query.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.global.AbstractMySqlContainerTest;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.user.domain.Profile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

@Slf4j
@JdbcTest
@DisplayName("UserJdbcRepository 통합 테스트")
@Sql(scripts = "/sql/user/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserJdbcRepositoryIntegrationTest extends AbstractMySqlContainerTest {

    @Autowired
    private UserJdbcRepository userJdbcRepository;

    @Nested
    @DisplayName("Find user by id ")
    class FindUserById {

        @Test
        @DisplayName("유저 이름 조회 성공")
        void success() {
            // * Given
            Long id = 1L;

            // * When
            var result = userJdbcRepository.findById(id);

            // * Then
            assertThat(result).isPresent();
        }

        @Test
        @DisplayName("Email 을 이용한 유저 이름 조회 성공")
        void successEmail() {
            // * Given
            String email = "u1@test.com";

            // * When
            var result = userJdbcRepository.findByEmail(email);

            // * Then
            assertThat(result).isPresent();
        }
    }

    @Nested
    @DisplayName("Find profile by id")
    class FindProfileById {

        @Test
        @DisplayName("profile 조회 성공")
        void success() {
            // * Given
            Long id = 1L;

            // * When
            Profile result = userJdbcRepository.findProfileByUserId(id)
                    .orElseThrow(() -> new BusinessException(
                            ErrorCode.USER_NOT_FOUND));

            // * Then
            assertThat(result.getName()).isEqualTo("User One");
        }
    }
}
