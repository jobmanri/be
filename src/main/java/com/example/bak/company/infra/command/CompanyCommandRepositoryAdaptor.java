package com.example.bak.company.infra.command;

import com.example.bak.company.application.command.port.CompanyCommandPort;
import com.example.bak.company.domain.Company;
import com.example.bak.company.infra.CompanyJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompanyCommandRepositoryAdaptor implements CompanyCommandPort {

    private final CompanyJpaRepository companyJpaRepository;

    @Override
    public Company save(Company company) {
        return companyJpaRepository.save(company);
    }

    @Override
    public Optional<Company> findById(Long companyId) {
        return companyJpaRepository.findById(companyId);
    }

    @Override
    public void deleteById(Long companyId) {
        companyJpaRepository.deleteById(companyId);
    }
}
