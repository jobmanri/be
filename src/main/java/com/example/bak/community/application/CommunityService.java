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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CompanyRepository companyRepository;
    private final CommunityRepository communityRepository;

    @Transactional(readOnly = true)
    public List<CommunityResult.Detail> getCommunities(Long companyId) {
        List<Community> communities = communityRepository.findByCompanyId(companyId);

        return communities.stream()
                .map(CommunityResult.Detail::from)
                .toList();
    }

    @Transactional
    public CommunityResult.CommunityId createCommunity(Long companyId, String name,
            String jobGroup) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        Community community = communityRepository.save(Community.create(name, jobGroup, company));

        return CommunityResult.CommunityId.from(community);
    }

    @Transactional
    public void updateCommunity(Long communityId, String name, String jobGroup) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMUNITY_NOT_FOUND));

        community.update(name, jobGroup);
    }

    public void deleteCommunity(Long communityId) {
        communityRepository.deleteById(communityId);
    }
}
