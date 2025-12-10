package com.example.bak.comment.application.command.port;

import com.example.bak.comment.application.command.port.dto.UserSnapShot;
import com.example.bak.user.domain.User;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserDataPortStub implements UserDataPort {

    private final ConcurrentHashMap<Long, UserSnapShot> store = new ConcurrentHashMap<>();

    public void save(User user) {
        store.put(user.getId(),
                new UserSnapShot(user.getId(), user.getProfile().getNickname()));
    }

    @Override
    public Optional<UserSnapShot> findById(Long userId) {
        return Optional.ofNullable(store.get(userId));
    }
}
