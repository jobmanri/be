package com.example.bak.feed.infra.query.jdbc;

import com.example.bak.feed.application.query.dto.FeedDetail;
import com.example.bak.feed.application.query.dto.FeedSummary;
import com.example.bak.feed.infra.query.jdbc.mapper.FeedDetailRowMapper;
import com.example.bak.feed.infra.query.jdbc.mapper.FeedSummaryRowMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedJdbcRepositoryImpl implements FeedJdbcRepository {

    private static final FeedSummaryRowMapper FEED_SUMMARY_MAPPER = new FeedSummaryRowMapper();
    private static final FeedDetailRowMapper FEED_DETAIL_MAPPER = new FeedDetailRowMapper();
    private static final String SUMMARY_SELECT = """
            SELECT
                f.id   AS id,
                f.title AS title,
                u.id   AS author_id,
                p.nickname AS author_nickname,
                c.id   AS community_id,
                c.name AS community_name,
                c.job_group AS community_job_group,
                COUNT(fc.id) AS comment_count
            FROM feeds f
                JOIN users u ON f.author_id = u.id
                JOIN profiles p ON p.user_id = u.id
                JOIN communities c ON f.community_id = c.id
                LEFT JOIN feed_comments fc ON fc.feed_id = f.id
            """;

    private static final String SUMMARY_GROUP_BY = """
            GROUP BY f.id, f.title, u.id, p.nickname, c.id, c.name, c.job_group
            """;

    private static final String DETAIL_SELECT = """
            SELECT
                f.id      AS id,
                f.title   AS title,
                f.content AS content,
                u.id      AS author_id,
                p.nickname AS author_nickname,
                c.id      AS community_id,
                c.name    AS community_name,
                c.job_group AS community_job_group
            FROM feeds f
                JOIN users u ON f.author_id = u.id
                JOIN profiles p ON p.user_id = u.id
                JOIN communities c ON f.community_id = c.id
            WHERE f.id = :feedId
            LIMIT 1
            """;

    private static final String DEFAULT_ORDER_BY = "ORDER BY f.id DESC";

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Page<FeedSummary> findAll(Pageable pageable) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());

        String sql = SUMMARY_SELECT + '\n' + SUMMARY_GROUP_BY + '\n'
                + buildOrderByClause(pageable) + '\n'
                + "LIMIT :limit OFFSET :offset";

        List<FeedSummary> content = jdbc.query(sql, params, FEED_SUMMARY_MAPPER);
        long total = countFeeds();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Optional<FeedSummary> findSummaryById(Long feedId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("feedId", feedId);

        String sql = SUMMARY_SELECT + '\n'
                + "WHERE f.id = :feedId\n"
                + SUMMARY_GROUP_BY;

        return jdbc.query(sql, params, FEED_SUMMARY_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<FeedDetail> findDetailById(Long feedId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("feedId", feedId);

        return jdbc.query(DETAIL_SELECT, params, FEED_DETAIL_MAPPER).stream().findFirst();
    }

    private long countFeeds() {
        Long total = jdbc.queryForObject("SELECT COUNT(*) FROM feeds", new MapSqlParameterSource(),
                Long.class);
        return total == null ? 0L : total;
    }

    private String buildOrderByClause(Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            return DEFAULT_ORDER_BY;
        }

        List<String> orderParts = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String column = mapPropertyToColumn(order.getProperty());
            if (column != null) {
                orderParts.add(column + " " + order.getDirection().name());
            }
        }

        if (orderParts.isEmpty()) {
            return DEFAULT_ORDER_BY;
        }

        return "ORDER BY " + String.join(", ", orderParts);
    }

    private String mapPropertyToColumn(String property) {
        return switch (property) {
            case "id" -> "f.id";
            case "title" -> "f.title";
            default -> null;
        };
    }
}
