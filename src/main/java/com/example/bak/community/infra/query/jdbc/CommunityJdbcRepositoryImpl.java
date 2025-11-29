package com.example.bak.community.infra.query.jdbc;

import com.example.bak.community.application.query.dto.CommunityResult;
import com.example.bak.community.infra.query.jdbc.mapper.CommunityDetailMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityJdbcRepositoryImpl implements CommunityJdbcRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<CommunityResult.Detail> findByCompanyId(Long companyId) {
        String sql = """
                SELECT
                    cm.id        AS id,
                    cm.name      AS name,
                    cm.job_group AS job_group
                FROM communities cm
                WHERE cm.company_id = :companyId;
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("companyId", companyId);

        return jdbc.query(sql, params, new CommunityDetailMapper());
    }
}
