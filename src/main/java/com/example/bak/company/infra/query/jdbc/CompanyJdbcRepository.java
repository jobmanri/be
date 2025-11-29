package com.example.bak.company.infra.query.jdbc;

import com.example.bak.company.application.query.dto.CompanyResult;
import java.util.List;
import java.util.Optional;

public interface CompanyJdbcRepository {

    List<CompanyResult.Flat> findAll();

    Optional<List<CompanyResult.Flat>> findById(Long id);
}
