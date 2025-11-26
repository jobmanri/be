package com.example.bak.community.application.command;

import com.example.bak.community.application.command.port.CommunityCommandPort;
import com.example.bak.community.application.command.port.CompanyValidationPort;
import com.example.bak.community.application.query.dto.CommunityResult;
import com.example.bak.community.domain.Community;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunityCommandService {

    private final CommunityCommandPort communityCommandPort;
    private final CompanyValidationPort companyValidationPort;

    public CommunityResult.CommunityId createCommunity(Long companyId, String name, String jobGroup) {
        validateCompanyId(companyId);

        Community community = communityCommandPort.save(Community.create(name, jobGroup, companyId));

        return CommunityResult.CommunityId.from(community);
    }

    public void updateCommunity(Long communityId, String name, String jobGroup) {
        Community community = communityCommandPort.findById(communityId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMUNITY_NOT_FOUND));

        community.update(name, jobGroup);
    }

    public void deleteCommunity(Long communityId) {
        communityCommandPort.deleteById(communityId);
    }

    private void validateCompanyId(Long companyId) {
        if (!companyValidationPort.isExistCompanyId(companyId)) {
            throw new BusinessException(ErrorCode.COMPANY_NOT_FOUND);
        }
    }
}
