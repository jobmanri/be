package com.example.bak.company.application.query;

import com.example.bak.company.application.query.dto.CompanyResult;
import com.example.bak.company.application.query.dto.CompanyResult.Detail;
import com.example.bak.company.application.query.dto.CompanyResult.Flat;
import com.example.bak.company.application.query.port.CompanyQueryPort;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyQueryService {

    private final CompanyQueryPort companyQueryPort;

    public List<CompanyResult.Detail> getCompanies() {
        Map<Long, List<Flat>> groupedByCompanyId = companyQueryPort.findAll().stream()
                .collect(Collectors.groupingBy(Flat::companyId));

        return groupedByCompanyId.values().stream()
                .map(Detail::from)
                .toList();
    }

    public CompanyResult.Detail getCompany(Long companyId) {
        List<Flat> flats = companyQueryPort.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        return CompanyResult.Detail.from(flats);
    }
}
