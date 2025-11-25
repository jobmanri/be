package com.example.bak.company.infra.query.jdbc;

import com.example.bak.company.application.query.dto.CompanyResult;
import com.example.bak.company.infra.query.jdbc.mapper.CompanyResultFlatMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompanyJdbcRepositoryImpl implements CompanyJdbcRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<CompanyResult.Flat> findAll() {

        String sql = """
                 SELECT
                     c.id AS companyId,
                     c.name AS companyName,
                     c.career_link AS careerLink,
                     c.logo_url AS logoUrl,
                     c.description AS description,
                     cm.id AS communityId,
                     cm.name AS communityName,
                     cm.job_group AS jobGroup
                 FROM companies c
                 LEFT JOIN communities cm
                     ON c.id = cm.company_id
                 ORDER BY c.id;
                """;

        return jdbc.query(sql, new CompanyResultFlatMapper());
    }


    @Override
    public Optional<CompanyResult.Detail> findById(Long id) {
        return Optional.empty();
    }
}
