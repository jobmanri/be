package com.example.bak.community.application.query;

import com.example.bak.community.application.query.dto.CommunityResult;
import com.example.bak.community.application.query.port.CommunityQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityQueryService {

    private final CommunityQueryPort communityQueryPort;

    public List<CommunityResult.Detail> getCommunities(Long companyId) {
        return communityQueryPort.findByCompanyId(companyId);
    }
}
