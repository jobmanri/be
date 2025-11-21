package com.example.bak.privatemessage.infra.query.jdbc;

import com.example.bak.privatemessage.application.query.dto.MessageItemResult;
import com.example.bak.privatemessage.application.query.dto.MessagePartnerResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MessageJdbcRepositoryImpl implements MessageJdbcRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<MessagePartnerResult> findPartnersByUserId(Long userId) {

        String sql = """
                SELECT
                    t.partner_id AS partner_id,
                    p.nickname AS nickname,
                    CASE
                        WHEN u.unread_count > 0 THEN TRUE
                        ELSE FALSE
                    END AS has_unread
                FROM (
                    SELECT pm.partner_id
                    FROM (
                        SELECT receiver_id AS partner_id
                        FROM private_messages
                        WHERE sender_id = :userId
                          AND deleted_at_by_sender IS NULL
                        UNION ALL
                        SELECT sender_id AS partner_id
                        FROM private_messages
                        WHERE receiver_id = :userId
                          AND deleted_at_by_receiver IS NULL
                    ) pm
                    GROUP BY pm.partner_id
                ) t
                LEFT JOIN (
                    SELECT sender_id AS partner_id, COUNT(*) AS unread_count
                    FROM private_messages
                    WHERE receiver_id = :userId
                      AND read_at IS NULL
                      AND deleted_at_by_receiver IS NULL
                    GROUP BY sender_id
                ) u ON u.partner_id = t.partner_id
                JOIN profiles p ON p.user_id = t.partner_id
                
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);

        return jdbc.query(sql, params, (rs, rowNum) ->
                new MessagePartnerResult(
                        rs.getLong("partner_id"),
                        rs.getString("nickname"),
                        rs.getInt("has_unread") == 1
                )
        );
    }

    @Override
    public List<MessageItemResult> findMessagesBetweenUsers(Long fromUserId, Long toUserId) {

        String sql = """
                SELECT 
                    id,
                    sender_id,
                    receiver_id,
                    content,
                    read_at
                FROM private_messages
                WHERE (sender_id = :fromUserId AND receiver_id = :toUserId)
                   OR (sender_id = :toUserId AND receiver_id = :fromUserId)
                order by id desc
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("fromUserId", fromUserId)
                .addValue("toUserId", toUserId);

        return jdbc.query(sql, params, (rs, rowNum) ->
                new MessageItemResult(
                        rs.getLong("id"),
                        rs.getLong("sender_id"),
                        rs.getLong("receiver_id"),
                        rs.getString("content"),
                        rs.getTimestamp("read_at") == null ? null
                                : rs.getTimestamp("read_at").toLocalDateTime()
                )
        );
    }

}
