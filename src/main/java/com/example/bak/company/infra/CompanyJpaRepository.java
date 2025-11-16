package com.example.bak.company.infra;

import com.example.bak.company.domain.Company;
import com.example.bak.company.domain.CompanyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<Company, Integer>, CompanyRepository {

    @Override
    Company save(Company company);
}