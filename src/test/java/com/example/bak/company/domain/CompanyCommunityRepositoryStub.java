package com.example.bak.company.domain;

import com.example.bak.community.domain.Community;
import com.example.bak.global.support.AbstractStubRepository;
import java.util.Objects;

public class CompanyCommunityRepositoryStub
        extends AbstractStubRepository<Long, Community> {

    @Override
    protected Long getId(Community community) {
        return community.getId();
    }

    @Override
    protected boolean isSame(Long left, Long right) {
        return Objects.equals(left, right);
    }
}
