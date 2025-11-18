package com.example.bak.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.bak.global.exception.BusinessException;
import com.example.bak.user.domain.User;
import com.example.bak.user.domain.UserRepositoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("UserService 유닛 테스트")
class UserServiceUnitTest {

    private UserService userService;
    private UserRepositoryStub userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryStub();
        userService = new UserService(userRepository);
    }

    private User setupUser() {
        User user = User.testInstance(
                1L,
                "test@test.com",
                "password",
                "name",
                "nickname"
        );
        userRepository.save(user);
        return user;
    }

    @Nested
    @DisplayName("createUser")
    class CreateUserTest {

        @Test
        @DisplayName("유저 생성에 성공한다")
        void createUser_success() {

            // given
            String email = "test@test.com";
            String password = "password";
            String name = "name";
            String nickname = "nickname";

            // when
            var result = userService.createUser(email, password, name, nickname);

            // then
            assertThat(result.id()).isNull();

            User saved = userRepository.findAll().getFirst();
            assertThat(saved.getEmail()).isEqualTo(email);
            assertThat(saved.getProfile().getName()).isEqualTo(name);
        }
    }

    @Nested
    @DisplayName("getUserProfile")
    class GetUserProfileTest {

        @Test
        @DisplayName("프로필 조회에 성공한다")
        void getUserProfile_success() {

            // given
            User user = setupUser();

            // when
            var profile = userService.getUserProfile(user.getId());

            // then
            assertThat(profile.name()).isEqualTo("name");
            assertThat(profile.nickname()).isEqualTo("nickname");
        }

        @Test
        @DisplayName("존재하지 않는 사용자는 예외를 던진다")
        void getUserProfile_notFound() {

            // given
            setupUser();

            // when & then
            assertThrows(
                    BusinessException.class,
                    () -> userService.getUserProfile(999L)
            );
        }
    }
}
