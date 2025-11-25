package com.example.bak.company.infra.query.jdbc.mapper;

import com.example.bak.company.application.query.dto.CompanyResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CompanyResultFlatMapper implements RowMapper<CompanyResult.Flat> {

    @Override
    public CompanyResult.Flat mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CompanyResult.Flat(
                rs.getLong("company_id"),
                rs.getString("company_name"),
                rs.getString("career_link"),
                rs.getString("logo_url"),
                rs.getString("description"),
                rs.getLong("community_id"),
                rs.getString("community_name"),
                rs.getString("job_group")
        );
    }
}
