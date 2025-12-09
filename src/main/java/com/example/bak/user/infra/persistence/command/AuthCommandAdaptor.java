package com.example.bak.user.infra.persistence.command;

import com.example.bak.auth.application.command.port.AuthCommandPort;
import com.example.bak.user.domain.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthCommandAdaptor implements AuthCommandPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public void register(String email, String password) {
        User user = User.create(email, password);
        userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }
}
