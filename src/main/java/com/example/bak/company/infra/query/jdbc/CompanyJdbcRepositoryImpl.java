package com.example.bak.company.infra.query.jdbc;

import com.example.bak.company.application.query.dto.CompanyResult;
import com.example.bak.company.application.query.dto.CompanyResult.Flat;
import com.example.bak.company.infra.query.jdbc.mapper.CompanyResultFlatMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
                     c.id AS company_id,
                     c.name AS company_name,
                     c.career_link AS career_link,
                     c.logo_url AS logo_url,
                     c.description AS description,
                     cm.id AS community_id,
                     cm.name AS community_name,
                     cm.job_group AS job_group
                 FROM companies c
                 LEFT JOIN communities cm
                     ON c.id = cm.company_id
                 ORDER BY c.id;
                """;

        return jdbc.query(sql, new CompanyResultFlatMapper());
    }


    @Override
    public Optional<List<CompanyResult.Flat>> findById(Long id) {
        String sql = """
                 SELECT
                     c.id AS company_id,
                     c.name AS company_name,
                     c.career_link AS career_link,
                     c.logo_url AS logo_url,
                     c.description AS description,
                     cm.id AS community_id,
                     cm.name AS community_name,
                     cm.job_group AS job_group
                 FROM companies c
                 LEFT JOIN communities cm
                     ON c.id = cm.company_id
                 WHERE c.id = :companyId
                 ORDER BY c.id;
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("companyId", id);

        List<Flat> flats = jdbc.query(sql, params, new CompanyResultFlatMapper());

        return flats.isEmpty() ?
                Optional.empty() :
                Optional.of(flats);
    }
}
