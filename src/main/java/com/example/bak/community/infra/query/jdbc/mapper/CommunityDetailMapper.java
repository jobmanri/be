package com.example.bak.community.infra.query.jdbc.mapper;

import com.example.bak.community.application.query.dto.CommunityResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CommunityDetailMapper implements RowMapper<CommunityResult.Detail> {

    @Override
    public CommunityResult.Detail mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CommunityResult.Detail(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("job_group")
        );
    }
}
