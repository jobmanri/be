package com.example.bak.privatemessage.infra.query.jdbc.mapper;

import com.example.bak.privatemessage.application.query.dto.MessageCorrespondentResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class MessageCorrespondentRowMapper implements RowMapper<MessageCorrespondentResult> {

    @Override
    public MessageCorrespondentResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MessageCorrespondentResult(
                rs.getLong("partner_id"),
                rs.getString("nickname"),
                rs.getBoolean("has_unread")
        );
    }
}