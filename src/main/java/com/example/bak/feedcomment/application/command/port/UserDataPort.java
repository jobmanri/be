package com.example.bak.feedcomment.application.command.port;

import com.example.bak.feedcomment.application.command.port.dto.UserSnapShot;
import java.util.Optional;

public interface UserDataPort {

    Optional<UserSnapShot> findById(Long userId);
}
