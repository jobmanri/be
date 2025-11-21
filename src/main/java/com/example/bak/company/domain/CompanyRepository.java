package com.example.bak.company.domain;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository {

    List<Company> findAll();

    Optional<Company> findById(Long companyId);

    Company save(Company company);

    void deleteById(Long companyId);
}
