package com.example.bak.user.infra.persistence.command;

import com.example.bak.user.domain.User;
import com.example.bak.user.domain.UserRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {

    @Override
    User save(User user);

    @Override
    Optional<User> findById(Long id);

    @Override
    Optional<User> findByEmail(String email);
}
