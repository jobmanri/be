package com.example.bak.company.application.query.dto;

import com.example.bak.community.application.dto.CommunityResult;
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

        public static Detail from(List<Flat> flats) {
            Flat first = flats.getFirst();

            if (first.communityId() == 0) {
                return new Detail(
                        first.companyId(),
                        first.name(),
                        first.careerLink(),
                        first.logoUrl(),
                        first.description(),
                        List.of()
                );
            }

            return new Detail(
                    first.companyId(),
                    first.name(),
                    first.careerLink(),
                    first.logoUrl(),
                    first.description(),
                    flats.stream()
                            .map(CommunityResult.Detail::from)
                            .toList()
            );
        }
    }

    public record Flat(
            Long companyId,
            String name,
            String careerLink,
            String logoUrl,
            String description,
            Long communityId,
            String communityName,
            String jobGroup
    ) {
    }
}
