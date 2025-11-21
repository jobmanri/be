package com.example.bak.company.application;

import com.example.bak.community.domain.Community;
import com.example.bak.community.domain.CommunityRepository;
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
    private final CommunityRepository communityRepository;

    @Transactional(readOnly = true)
    public List<CompanyResult.Detail> getCompanies() {
        List<Company> companies = companyRepository.findAll();

        return companies.stream()
                .map(company -> {
                    List<Community> communities = communityRepository.findByCompanyId(
                            company.getId());
                    return CompanyResult.Detail.from(company, communities);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public CompanyResult.Detail getCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        List<Community> communities = communityRepository.findByCompanyId(companyId);

        return CompanyResult.Detail.from(company, communities);
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

    public void deleteCompany(Long companyId) {
        companyRepository.deleteById(companyId);
    }
}
