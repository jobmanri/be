package com.example.bak.user.infra.persistence.query.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.global.AbstractMySqlContainerTest;
import com.example.bak.user.domain.Profile;
import com.example.bak.user.domain.User;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

@Slf4j
@JdbcTest
@DisplayName("ProfileJdbcRepository 통합 테스트")
@Sql(scripts = "/sql/user/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProfileJdbcRepositoryImplTest extends AbstractMySqlContainerTest {

    @Autowired
    private ProfileJdbcRepository profileJdbcRepository;

    @Nested
    @DisplayName("Profile 조회 관련 Tests")
    class ProfileFind {

        @Test
        void findById() {
            Long profileId = 1L;
            Optional<Profile> profile = profileJdbcRepository.findById(profileId);

            assertThat(profile).isNotEmpty();

            assertThat(profile.get().getId()).isEqualTo(profileId);
        }

        @Test
        void findByName() {
            String username = "User One";
            Optional<Profile> profile = profileJdbcRepository.findByName(username);

            assertThat(profile).isNotEmpty();

            assertThat(profile.get().getName()).isEqualTo(username);
        }
    }

    @Nested
    @DisplayName("User 조회 관련 Test")
    class UserFind {

        @Test
        void findUserById() {
            Long userId = 1L;

            Optional<User> user = profileJdbcRepository.findUserById(userId);

            assertThat(user).isNotEmpty();

            assertThat(user.get().getId()).isEqualTo(userId);
        }
    }


}
