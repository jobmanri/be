package com.example.bak.user.application.command;

import com.example.bak.user.application.command.port.UserCommandPort;
import com.example.bak.user.application.query.dto.ProfileResult;
import com.example.bak.user.domain.User;
import com.example.bak.user.infra.command.support.UserCommandPortStub;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserCommandService 유닛 테스트")
class UserCommandServiceTest {

    private UserCommandService service;
    private UserCommandPort port;

    @BeforeEach
    void setUp() {
        port = new UserCommandPortStub();
        service = new UserCommandService(port);
    }

    @Test
    @DisplayName("User 생성 로직 Check")
    void 유저_생성_Check_Test_Method() {
        // * Given
        Long userId = 1L;
        String email = "test";
        String password = "testId!@1224";

        // * When
        User user = User.createInstance(userId, email, password);
        User result = port.save(user);

        // * Then
        Assertions.assertThat(result.getId()).isEqualTo(userId);
    }


    @Test
    @DisplayName("Profile 생성 로직 Check")
    void Profile_생성_Check_Test_Method() {
        // * Given
        Long userId = 1L;
        String name = "test";
        String nickname = "testId!@123";

        // * When
        ProfileResult result = service.createProfile(userId, name, nickname);

        // * Then
        Assertions.assertThat(result.name()).isEqualTo(name);
    }
}