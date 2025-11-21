package com.example.bak.company.application.dto;

import com.example.bak.community.application.dto.CommunityResult;
import com.example.bak.community.domain.Community;
import com.example.bak.company.domain.Company;
import java.util.List;

public class CompanyResult {

    public record CompanyId(
            Long value
    ) {

        public static CompanyId from(Company company) {
            return new CompanyId(company.getId());
        }
    }

    public record Detail(
            Long companyId,
            String name,
            String careerLink,
            String logoUrl,
            String description,
            List<CommunityResult.Detail> communityInfos
    ) {

        public static Detail from(Company company, List<Community> communities) {
            return new Detail(
                    company.getId(),
                    company.getName(),
                    company.getCareerLink(),
                    company.getLogoUrl(),
                    company.getDescription(),
                    communities.stream().map(CommunityResult.Detail::from).toList()
            );
        }
    }
}
