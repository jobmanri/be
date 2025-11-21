package com.example.bak.company.application;

import com.example.bak.company.application.dto.CompanyResult;
import com.example.bak.company.application.dto.CompanyResult.CompanyId;
import com.example.bak.company.domain.Company;
import com.example.bak.company.domain.CompanyRepository;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public List<CompanyResult.Detail> getCompanies() {
        List<Company> companies = companyRepository.findAll();

        return companies.stream()
                .map(CompanyResult.Detail::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public CompanyResult.Detail getCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        return CompanyResult.Detail.from(company);
    }

    @Transactional
    public CompanyId createCompany(
            String name, String careerLink, String logoUrl, String description
    ) {
        Company company = Company.create(name, careerLink, logoUrl, description);
        return CompanyId.from(companyRepository.save(company));
    }

    @Transactional
    public void updateCompany(
            Long companyId, String name, String careerLink, String logoUrl, String description
    ) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        company.update(name, careerLink, logoUrl, description);
    }

    @Transactional
    public void deleteCompany(Long companyId) {
        companyRepository.deleteById(companyId);
    }
}
