package com.example.bak.user.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    User save(User user);

    Optional<Profile> findProfileByUserId(Long userId);

    Optional<User> findUserById(Long id);
}
