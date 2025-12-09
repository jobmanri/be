package com.example.bak.auth.application.command.port;


import com.example.bak.user.domain.User;
import java.util.Optional;

public interface AuthCommandPort {

    void register(String email, String password);

    Optional<User> findByEmail(String email);
}
