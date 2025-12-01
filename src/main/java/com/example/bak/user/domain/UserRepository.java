package com.example.bak.user.domain;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<Profile> findProfileByUserId(Long userId);

    Optional<User> findById(Long userId);

    Optional<User> findByEmail(String email);
}
