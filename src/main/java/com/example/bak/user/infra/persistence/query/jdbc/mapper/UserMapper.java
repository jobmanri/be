package com.example.bak.user.infra.persistence.query.jdbc.mapper;


import com.example.bak.user.domain.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.createInstance(
                rs.getLong("user_id"),
                rs.getString("user_email"),
                rs.getString("user_password")
        );
    }
}
