package com.example.bak.company.infra.command;

import com.example.bak.community.application.command.CompanyValidationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompanyValidationAdaptor implements CompanyValidationPort {

    private final CompanyJpaRepository companyJpaRepository;

    @Override
    public boolean isExistCompanyId(Long id) {
        return companyJpaRepository.existsById(id);
    }
}
