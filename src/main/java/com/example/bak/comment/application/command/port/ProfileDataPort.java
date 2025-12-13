package com.example.bak.comment.application.command.port;

import com.example.bak.comment.domain.ProfileSnapShot;
import java.util.Optional;

public interface ProfileDataPort {

    Optional<ProfileSnapShot> findSnapshotByUserId(Long userId);
}
