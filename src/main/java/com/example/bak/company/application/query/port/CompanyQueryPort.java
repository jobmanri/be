package com.example.bak.company.application.query.port;

import com.example.bak.company.application.query.dto.CompanyResult;
import java.util.List;
import java.util.Optional;

public interface CompanyQueryPort {

    List<CompanyResult.Flat> findAll();

    Optional<CompanyResult.Detail> findById(Long companyId);
}
