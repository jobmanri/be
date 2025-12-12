package com.example.bak.user.infra.persistence.query;

import com.example.bak.comment.application.command.port.UserDataPort;
import com.example.bak.comment.application.command.port.dto.ProfileSnapShot;
import com.example.bak.user.infra.persistence.command.ProfileJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDataAdapter implements UserDataPort {

    private final ProfileJpaRepository profileJpaRepository;

    @Override
    public Optional<ProfileSnapShot> findById(Long userId) {
        return profileJpaRepository.findProfileSnapShotByUserId(userId);
    }

}