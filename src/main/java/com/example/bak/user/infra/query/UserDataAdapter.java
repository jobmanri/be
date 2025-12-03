package com.example.bak.user.infra.query;

import com.example.bak.feedcomment.application.command.port.UserDataPort;
import com.example.bak.feedcomment.application.command.port.UserDataPort.UserSnapshot;
import com.example.bak.user.domain.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDataAdapter implements UserDataPort {

    private final UserRepository userRepository;

    @Override
    public Optional<UserSnapshot> findById(Long userId) {
        return userRepository.findById(userId)
                .map(user -> new UserSnapshot(
                        user.getId(),
                        user.getProfile().getNickname()
                ));
    }
}
