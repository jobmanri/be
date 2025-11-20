package com.example.bak.company.application.dto;

import com.example.bak.company.domain.Company;
import java.util.List;

public class CompanyResult {

    public record ResourcePath(
            Long companyId
    ) {

        public static ResourcePath from(Company company) {
            return new ResourcePath(company.getId());
        }
    }

    public record Detail(
            Long companyId,
            String name,
            String careerLink,
            String logoUrl,
            String description,
            List<CommunityInfo> communityInfos
    ) {

        public static Detail from(Company company) {
            return new Detail(
                    company.getId(),
                    company.getName(),
                    company.getCareerLink(),
                    company.getLogoUrl(),
                    company.getDescription(),
                    company.getCommunities().stream().map(CommunityInfo::from).toList()
            );
        }
    }
}
