package com.example.bak.community.infra.query;


import com.example.bak.community.application.query.dto.CommunityResult;
import com.example.bak.community.application.query.port.CommunityQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityQueryAdaptor implements CommunityQueryPort {


    @Override
    public List<CommunityResult.Detail> findByCompanyId(Long id) {
        return List.of();
    }
}
