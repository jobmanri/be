package com.example.bak.user.infra.persistence.query.jdbc.mapper;

import com.example.bak.user.application.query.dto.UserResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserResultMapper implements RowMapper<UserResult> {

    @Override
    public UserResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserResult.from(
                rs.getLong("user_id"),
                rs.getString("nickname")
        );
    }
}
