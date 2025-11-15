package com.example.bak.user.infra.persistence;

import com.example.bak.user.domain.User;
import com.example.bak.user.domain.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {
    @Override
    List<User> findAll();
    
    @Override
    User save(User user);
}
