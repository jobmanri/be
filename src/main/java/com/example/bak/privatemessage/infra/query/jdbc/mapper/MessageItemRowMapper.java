package com.example.bak.privatemessage.infra.query.jdbc.mapper;

import com.example.bak.privatemessage.application.query.dto.MessageItemResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class MessageItemRowMapper implements RowMapper<MessageItemResult> {

    @Override
    public MessageItemResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MessageItemResult(
                rs.getLong("id"),
                rs.getLong("sender_id"),
                rs.getLong("receiver_id"),
                rs.getString("content"),
                rs.getTimestamp("read_at") == null
                        ? null
                        : rs.getTimestamp("read_at").toLocalDateTime()
        );
    }
}