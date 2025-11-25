package com.example.bak.community.domain;

import com.example.bak.global.support.AbstractStubRepository;
import java.util.List;
import java.util.Objects;

public class CommunityRepositoryStub
        extends AbstractStubRepository<Long, Community>
        implements CommunityRepository {

    @Override
    protected Long getId(Community community) {
        return community.getId();
    }

    @Override
    protected boolean isSame(Long left, Long right) {
        return Objects.equals(left, right);
    }

    @Override
    public List<Community> findByCompanyId(Long companyId) {
        return findAll().stream()
                .filter(community -> community.getCompany().getId().equals(companyId))
                .toList();
    }
}
