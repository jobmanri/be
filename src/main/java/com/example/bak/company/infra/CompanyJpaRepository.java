package com.example.bak.company.infra;

import com.example.bak.company.domain.Company;
import com.example.bak.company.domain.CompanyRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<Company, Integer>, CompanyRepository {

    @Override
    List<Company> findAll();

    @Override
    Optional<Company> findById(Long companyId);

    @Override
    Company save(Company company);

    @Override
    void deleteById(Long companyId);
}
