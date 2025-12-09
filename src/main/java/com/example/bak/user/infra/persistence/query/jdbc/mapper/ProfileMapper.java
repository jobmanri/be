package com.example.bak.user.infra.persistence.query.jdbc.mapper;


import com.example.bak.user.domain.Profile;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ProfileMapper implements RowMapper<Profile> {

    @Override
    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Profile.createInstance(
                rs.getLong("profile_id"),
                rs.getString("profile_name"),
                rs.getString("profile_nickname")
        );
    }
}
