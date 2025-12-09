package com.example.bak.user.infra.query;

import com.example.bak.feedcomment.application.command.port.UserDataPort;
import com.example.bak.feedcomment.application.command.port.dto.UserSnapShot;
import com.example.bak.user.domain.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDataAdapter implements UserDataPort {

    private final UserRepository userRepository;

    @Override
    public Optional<UserSnapShot> findById(Long userId) {
        return userRepository.findById(userId)
                .map(user -> new UserSnapShot(
                        user.getId(),
                        user.getProfile().getNickname()
                ));
    }
}
