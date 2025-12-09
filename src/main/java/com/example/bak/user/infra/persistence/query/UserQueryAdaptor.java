package com.example.bak.user.infra.persistence.query;

import com.example.bak.user.application.query.dto.UserResult;
import com.example.bak.user.application.query.port.UserQueryPort;
import com.example.bak.user.domain.Profile;
import com.example.bak.user.domain.User;
import com.example.bak.user.infra.persistence.query.jdbc.UserJdbcRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryAdaptor implements UserQueryPort {

    private final UserJdbcRepository repository;

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<Profile> findProfileByUserId(Long userId) {
        return repository.findProfileByUserId(userId);
    }

    @Override
    public Optional<UserResult> getUserInfoById(Long userId) {
        return repository.getUserInfoById(userId);
    }
}
