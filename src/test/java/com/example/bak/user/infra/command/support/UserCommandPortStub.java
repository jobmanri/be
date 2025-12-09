package com.example.bak.user.infra.command.support;

import com.example.bak.global.support.AbstractStubRepository;
import com.example.bak.user.application.command.port.UserCommandPort;
import com.example.bak.user.application.query.dto.ProfileResult;
import com.example.bak.user.domain.Profile;
import com.example.bak.user.domain.User;
import java.util.Objects;

public class UserCommandPortStub extends AbstractStubRepository<Long, User> implements
        UserCommandPort {

    @Override
    protected Long getId(User user) {
        return user.getId();
    }

    @Override
    protected boolean isSame(Long left, Long right) {
        return Objects.equals(left, right);
    }

    @Override
    public ProfileResult createProfile(Long userId, String name, String nickname) {
        Profile profile = Profile.createInstance(userId, name, nickname);
        return ProfileResult.from(profile);
    }
}
