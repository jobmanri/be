package com.example.bak.comment.application.command.port;

import com.example.bak.comment.application.command.port.dto.ProfileSnapShot;
import java.util.Optional;

public interface UserDataPort {

    Optional<ProfileSnapShot> findById(Long userId);
}
