package com.example.bak.comment.application.command.port;

import com.example.bak.comment.application.command.port.dto.ProfileSnapShot;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserDataPortStub implements UserDataPort {

    private final ConcurrentHashMap<Long, ProfileSnapShot> store = new ConcurrentHashMap<>();

    public void save(Long userId, String nickname) {
        store.put(userId, new ProfileSnapShot(userId, nickname));
    }

    @Override
    public Optional<ProfileSnapShot> findById(Long userId) {
        return Optional.ofNullable(store.get(userId));
    }
}
