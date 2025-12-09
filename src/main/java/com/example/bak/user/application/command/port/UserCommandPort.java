package com.example.bak.user.application.command.port;

import com.example.bak.user.application.query.dto.ProfileResult;
import com.example.bak.user.domain.User;

public interface UserCommandPort {

    User save(User user);

    ProfileResult createProfile(Long userId, String name, String nickname);
}
