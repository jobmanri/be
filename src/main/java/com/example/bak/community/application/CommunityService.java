package com.example.bak.community.application;

import com.example.bak.community.application.dto.CommunityResult;
import com.example.bak.community.domain.Community;
import com.example.bak.community.domain.CommunityRepository;
import com.example.bak.company.domain.Company;
import com.example.bak.company.domain.CompanyRepository;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CompanyRepository companyRepository;
    private final CommunityRepository communityRepository;

    public List<CommunityResult> getCompanyCommunities(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        return company.getCommunities().stream()
                .map(CommunityResult::from)
                .toList();
    }

    public void createCompanyCommunity(Long companyId, String name, String jobGroup) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        Community community = Community.create(name, jobGroup);

        company.addCommunity(community);
    }

    public void updateCompanyCommunity(
            Long companyId, Long companyCommunityId, String name, String jobGroup
    ) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        Community community = company.getCommunities().stream()
                .filter(target -> target.getId().equals(companyCommunityId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        community.update(name, jobGroup);
    }

    public void deleteCompanyCommunity(Long companyId, Long companyCommunityId) {

    }
}
