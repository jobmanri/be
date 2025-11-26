package com.example.bak.community.domain;

import com.example.bak.community.application.command.port.CompanyValidationPort;
import com.example.bak.company.domain.Company;
import com.example.bak.global.support.AbstractStubRepository;
import java.util.Objects;

public class CompanyValidationStub
        extends AbstractStubRepository<Long, Company>
        implements CompanyValidationPort {

    @Override
    protected Long getId(Company company) {
        return company.getId();
    }

    @Override
    protected boolean isSame(Long left, Long right) {
        return Objects.equals(left, right);
    }

    @Override
    public boolean isExistCompanyId(Long id) {
        return store.stream()
                .anyMatch(company -> isSame(company.getId(), id));
    }
}
