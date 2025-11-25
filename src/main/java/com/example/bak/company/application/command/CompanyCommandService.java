package com.example.bak.company.application.command;

import com.example.bak.company.application.command.port.CompanyCommandPort;
import com.example.bak.company.application.dto.CompanyResult.CompanyId;
import com.example.bak.company.domain.Company;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CompanyCommandService {

    private final CompanyCommandPort companyCommandPort;

    public CompanyId createCompany(
            String name, String careerLink, String logoUrl, String description
    ) {
        Company company = Company.create(name, careerLink, logoUrl, description);
        return CompanyId.from(companyCommandPort.save(company));
    }

    public void updateCompany(
            Long companyId, String name, String careerLink, String logoUrl, String description
    ) {
        Company company = companyCommandPort.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        company.update(name, careerLink, logoUrl, description);
    }

    public void deleteCompany(Long companyId) {
        companyCommandPort.deleteById(companyId);
    }
}
