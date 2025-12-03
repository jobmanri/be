package com.example.bak.feedcomment.application.command.port;

import java.util.Optional;

public interface UserDataPort {

    Optional<UserSnapshot> findById(Long userId);

    record UserSnapshot(Long id, String nickname) {

    }
}
