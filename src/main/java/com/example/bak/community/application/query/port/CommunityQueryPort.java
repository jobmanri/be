package com.example.bak.community.application.query.port;

import com.example.bak.community.application.query.dto.CommunityResult;
import java.util.List;

public interface CommunityQueryPort {

    List<CommunityResult.Detail> findByCompanyId(Long id);
}
