package com.example.bak.company.domain;

import com.example.bak.global.support.AbstractStubRepository;
import java.util.Objects;

public class CompanyCommunityRepositoryStub
        extends AbstractStubRepository<Long, CompanyCommunity>
        implements CompanyCommunityRepository {

    @Override
    protected Long getId(CompanyCommunity community) {
        return community.getId();
    }

    @Override
    protected boolean isSame(Long left, Long right) {
        return Objects.equals(left, right);
    }
}
