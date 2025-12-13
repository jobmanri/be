package com.example.bak.user.application.command;

import com.example.bak.user.application.command.dto.UserCommandResult;
import com.example.bak.user.application.command.port.UserCommandPort;
import com.example.bak.user.application.query.dto.ProfileResult;
import com.example.bak.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserCommandPort userCommandPort;

    @Transactional
    public UserCommandResult createUser(String email, String password) {
        User user = User.create(email, password);
        User savedUser = userCommandPort.save(user);

        return UserCommandResult.of(savedUser.getId());
    }

    @Transactional
    public ProfileResult createProfile(Long userId, String name, String nickname) {
        return userCommandPort.createProfile(userId, name, nickname);
    }
}
