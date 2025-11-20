package com.example.bak.company.application;

import com.example.bak.company.application.dto.CompanyResult;
import com.example.bak.company.domain.Company;
import com.example.bak.company.domain.CompanyRepository;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<CompanyResult> getCompanies() {
        List<Company> companies = companyRepository.findAll();

        return companies.stream()
                .map(CompanyResult::from)
                .toList();
    }

    public CompanyResult getCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        return CompanyResult.from(company);
    }

    public void createCompany(
            String name, String careerLink, String logoUrl, String description
    ) {
        Company company = Company.create(name, careerLink, logoUrl, description);
        Company savedCompany = companyRepository.save(company);
    }

    public void updateCompany(
            Long companyId, String name, String careerLink, String logoUrl, String description
    ) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        company.update(name, careerLink, logoUrl, description);
    }

    public void deleteCompany(Long companyId) {
        companyRepository.deleteById(companyId);
    }
}
