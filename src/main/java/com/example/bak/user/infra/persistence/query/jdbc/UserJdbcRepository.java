package com.example.bak.user.infra.persistence.query.jdbc;

import com.example.bak.user.application.query.dto.UserResult;
import com.example.bak.user.domain.Profile;
import com.example.bak.user.domain.User;
import java.util.Optional;

public interface UserJdbcRepository {

    Optional<User> findById(Long userId);

    Optional<User> findByEmail(String email);

    Optional<Profile> findProfileByUserId(Long userId);

    Optional<UserResult> getUserInfoById(Long userId);
}
