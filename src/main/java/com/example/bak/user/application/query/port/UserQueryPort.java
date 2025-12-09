package com.example.bak.user.application.query.port;

import com.example.bak.user.application.query.dto.UserResult;
import com.example.bak.user.domain.Profile;
import com.example.bak.user.domain.User;
import java.util.Optional;

public interface UserQueryPort {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<Profile> findProfileByUserId(Long userId);

    Optional<UserResult> getUserInfoById(Long userId);
}
