package com.example.bak.user.infra.persistence.command;

import com.example.bak.comment.application.command.port.dto.ProfileSnapShot;
import com.example.bak.user.domain.Profile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileJpaRepository extends JpaRepository<Profile, Long> {

    Optional<ProfileSnapShot> findProfileSnapShotByUserId(Long userId);
}

