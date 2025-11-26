package com.example.bak.company.domain;


import com.example.bak.company.application.command.port.CompanyCommandPort;
import com.example.bak.global.support.AbstractStubRepository;
import java.util.Objects;

public class CompanyRepositoryStub
        extends AbstractStubRepository<Long, Company>
        implements CompanyCommandPort {

    @Override
    protected Long getId(Company company) {
        return company.getId();
    }

    @Override
    protected boolean isSame(Long left, Long right) {
        return Objects.equals(left, right);
    }
}
