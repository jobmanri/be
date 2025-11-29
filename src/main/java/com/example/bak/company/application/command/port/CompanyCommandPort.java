package com.example.bak.company.application.command.port;

import com.example.bak.company.domain.Company;
import java.util.Optional;

public interface CompanyCommandPort {

    Company save(Company company);

    Optional<Company> findById(Long companyId);

    void deleteById(Long companyId);
}
