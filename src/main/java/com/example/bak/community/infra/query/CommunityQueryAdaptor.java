package com.example.bak.community.infra.query;


import com.example.bak.community.application.query.dto.CommunityResult;
import com.example.bak.community.application.query.port.CommunityQueryPort;
import com.example.bak.community.infra.query.jdbc.CommunityJdbcRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityQueryAdaptor implements CommunityQueryPort {

    private final CommunityJdbcRepository communityJdbcRepository;

    @Override
    public List<CommunityResult.Detail> findByCompanyId(Long companyId) {
        return communityJdbcRepository.findByCompanyId(companyId);
    }
}
