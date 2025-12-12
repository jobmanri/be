package com.example.bak.user.domain;

import com.example.bak.comment.application.command.port.ProfileDataPort;
import com.example.bak.comment.application.command.port.dto.ProfileSnapShot;
import com.example.bak.global.support.AbstractStubRepository;
import java.util.Objects;
import java.util.Optional;

public class ProfileRepositoryStub extends AbstractStubRepository<Long, Profile>
        implements ProfileDataPort {

    @Override
    protected Long getId(Profile profile) {
        return profile.getUserId();
    }

    @Override
    protected boolean isSame(Long left, Long right) {
        return Objects.equals(left, right);
    }

    @Override
    public Profile save(Profile profile) {
        if (profile.getUserId() == null) {
            throw new IllegalArgumentException("Profile must have assigned userId");
        }
        return super.save(profile);
    }

    @Override
    public Optional<ProfileSnapShot> findSnapshotByUserId(Long userId) {
        return super.findById(userId)
                .map(profile -> new ProfileSnapShot(profile.getUserId(), profile.getNickname()));
    }
}
