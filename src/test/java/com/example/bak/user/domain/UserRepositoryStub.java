package com.example.bak.user.domain;

import com.example.bak.global.support.AbstractStubRepository;
import java.util.Objects;
import java.util.Optional;

public class UserRepositoryStub
        extends AbstractStubRepository<Long, User>
        implements UserRepository {

    @Override
    protected Long getId(User user) {
        return user.getId();
    }

    @Override
    protected boolean isSame(Long left, Long right) {
        return Objects.equals(left, right);
    }

    @Override
    public User save(User entity) {
        return super.save(entity);
    }

    @Override
    public Optional<Profile> findProfileByUserId(Long userId) {
        return findById(userId).map(User::getProfile);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return findById(id);
    }
}