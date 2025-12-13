package com.example.bak.community.domain;

import com.example.bak.community.application.command.port.CommunityCommandPort;
import com.example.bak.global.support.AbstractStubRepository;
import java.util.Objects;

public class CommunityRepositoryStub
        extends AbstractStubRepository<Long, Community> implements CommunityCommandPort {

    @Override
    protected Long getId(Community community) {
        return community.getId();
    }

    @Override
    protected boolean isSame(Long left, Long right) {
        return Objects.equals(left, right);
    }
}
