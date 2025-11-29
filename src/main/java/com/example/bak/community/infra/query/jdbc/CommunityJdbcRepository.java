package com.example.bak.community.infra.query.jdbc;

import com.example.bak.community.application.query.dto.CommunityResult;
import java.util.List;

public interface CommunityJdbcRepository {

    List<CommunityResult.Detail> findByCompanyId(Long companyId);
}
