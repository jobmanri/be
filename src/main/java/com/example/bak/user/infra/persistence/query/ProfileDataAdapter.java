package com.example.bak.user.infra.persistence.query;

import com.example.bak.comment.application.command.port.ProfileDataPort;
import com.example.bak.comment.application.command.port.dto.ProfileSnapShot;
import com.example.bak.user.infra.persistence.command.ProfileJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProfileDataAdapter implements ProfileDataPort {

    private final ProfileJpaRepository profileJpaRepository;

    @Override
    public Optional<ProfileSnapShot> findSnapshotByUserId(Long userId) {
        return profileJpaRepository.findProfileSnapShotByUserId(userId);
    }

}
