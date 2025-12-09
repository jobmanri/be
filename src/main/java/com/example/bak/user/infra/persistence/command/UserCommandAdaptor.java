package com.example.bak.user.infra.persistence.command;

import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.user.application.command.port.UserCommandPort;
import com.example.bak.user.application.query.dto.ProfileResult;
import com.example.bak.user.domain.Profile;
import com.example.bak.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCommandAdaptor implements UserCommandPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public ProfileResult createProfile(Long userId, String name, String nickname) {
        User user = userJpaRepository.findById(userId).orElseThrow(() -> new BusinessException(
                ErrorCode.USER_NOT_FOUND));
        Profile profile = Profile.create(name, nickname);
        user.addProfile(profile);
        return ProfileResult.from(profile.getName(), profile.getNickname());
    }
}
