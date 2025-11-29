package com.example.bak.company.infra.query;

import com.example.bak.company.application.query.dto.CompanyResult;
import com.example.bak.company.application.query.port.CompanyQueryPort;
import com.example.bak.company.infra.query.jdbc.CompanyJdbcRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompanyQueryAdaptor implements CompanyQueryPort {

    private final CompanyJdbcRepository companyJdbcRepository;

    @Override
    public List<CompanyResult.Flat> findAll() {
        return companyJdbcRepository.findAll();
    }

    @Override
    public Optional<List<CompanyResult.Flat>> findById(Long companyId) {
        return companyJdbcRepository.findById(companyId);
    }
}
