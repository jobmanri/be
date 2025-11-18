package com.example.bak.user.infra.persistence;

import com.example.bak.user.domain.Profile;
import com.example.bak.user.domain.User;
import com.example.bak.user.domain.UserRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {

    @Override
    User save(User user);

    @Override
    @Query("select u.profile from users u where u.id = :userId")
    Optional<Profile> findProfileByUserId(Long userId);

    @Override
    Optional<User> findById(Long id);
}
