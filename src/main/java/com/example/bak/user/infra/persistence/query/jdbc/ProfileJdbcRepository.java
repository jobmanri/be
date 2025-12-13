package com.example.bak.user.infra.persistence.query.jdbc;

import com.example.bak.user.domain.Profile;
import com.example.bak.user.domain.User;
import java.util.Optional;

public interface ProfileJdbcRepository {

    Optional<Profile> findById(Long id);

    Optional<Profile> findByName(String name);

    Optional<User> findUserById(Long userId);
}

