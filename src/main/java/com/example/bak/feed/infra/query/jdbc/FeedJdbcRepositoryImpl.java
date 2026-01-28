package com.example.bak.feed.infra.query.jdbc;

import com.example.bak.feed.application.query.dto.FeedDetail;
import com.example.bak.feed.application.query.dto.FeedSummary;
import com.example.bak.feed.application.query.dto.FeedTitleOnly;
import com.example.bak.feed.infra.query.jdbc.mapper.FeedDetailRowMapper;
import com.example.bak.feed.infra.query.jdbc.mapper.FeedSummaryRowMapper;
import com.example.bak.feed.infra.query.jdbc.mapper.FeedTitleOnlyRowMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedJdbcRepositoryImpl implements FeedJdbcRepository {

    private static final FeedSummaryRowMapper feedSummaryMapper = new FeedSummaryRowMapper();
    private static final FeedDetailRowMapper feedDetailMapper = new FeedDetailRowMapper();
    private static final FeedTitleOnlyRowMapper feedTitleOnlyMapper = new FeedTitleOnlyRowMapper();
    private static final String SUMMARY_SELECT = """
            SELECT
                f.id   AS id,
                f.title AS title,
                f.content AS content,
                u.id   AS author_id,
                p.nickname AS author_nickname,
                c.id   AS community_id,
                c.name AS community_name,
                c.job_group AS community_job_group,
                COALESCE(comment_counts.comment_count, 0) AS comment_count,
                COALESCE(like_counts.like_count, 0) AS like_count
            FROM feeds f
                JOIN users u ON f.author_id = u.id
                JOIN profiles p ON p.user_id = u.id
                JOIN communities c ON f.community_id = c.id
                LEFT JOIN (
                    SELECT feed_id, COUNT(*) AS comment_count
                    FROM comments
                    GROUP BY feed_id
                ) comment_counts ON comment_counts.feed_id = f.id
                LEFT JOIN (
                    SELECT target_id AS feed_id, COUNT(*) AS like_count
                    FROM likes
                    WHERE target_type = 'FEED'
                    GROUP BY target_id
                ) like_counts ON like_counts.feed_id = f.id
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
                c.job_group AS community_job_group,
                COALESCE(comment_counts.comment_count, 0) AS comment_count,
                COALESCE(like_counts.like_count, 0) AS like_count
            FROM feeds f
                JOIN users u ON f.author_id = u.id
                JOIN profiles p ON p.user_id = u.id
                JOIN communities c ON f.community_id = c.id
                LEFT JOIN (
                    SELECT feed_id, COUNT(*) AS comment_count
                    FROM comments
                    GROUP BY feed_id
                ) comment_counts ON comment_counts.feed_id = f.id
                LEFT JOIN (
                    SELECT target_id AS feed_id, COUNT(*) AS like_count
                    FROM likes
                    WHERE target_type = 'FEED'
                    GROUP BY target_id
                ) like_counts ON like_counts.feed_id = f.id
            WHERE f.id = :feedId
            LIMIT 1
            """;

    private static final String DEFAULT_ORDER_BY = "ORDER BY f.id DESC";
    private static final String DETAIL_MAIN_SELECT = """
            SELECT
                f.id      AS id,
                f.title   AS title,
                f.content AS content,
                u.id      AS author_id,
                p.nickname AS author_nickname,
                c.id      AS community_id,
                c.name    AS community_name,
                c.job_group AS community_job_group,
                COALESCE(comment_counts.comment_count, 0) AS comment_count,
                COALESCE(like_counts.like_count, 0) AS like_count
            FROM feeds f
                JOIN users u ON f.author_id = u.id
                JOIN profiles p ON p.user_id = u.id
                JOIN communities c ON f.community_id = c.id
                LEFT JOIN (
                    SELECT feed_id, COUNT(*) AS comment_count
                    FROM comments
                    GROUP BY feed_id
                ) comment_counts ON comment_counts.feed_id = f.id
                LEFT JOIN (
                    SELECT target_id AS feed_id, COUNT(*) AS like_count
                    FROM likes
                    WHERE target_type = 'FEED'
                    GROUP BY target_id
                ) like_counts ON like_counts.feed_id = f.id
            ORDER BY f.id DESC
            LIMIT :limit
            """;
    private static final String TITLE_ONLY_SELECT = """
            SELECT
                f.id   AS id,
                f.title AS title,
                c.id   AS community_id,
                c.name AS community_name,
                c.job_group AS community_job_group,
                COALESCE(comment_counts.comment_count, 0) AS comment_count,
                COALESCE(like_counts.like_count, 0) AS like_count
            FROM feeds f
                JOIN communities c ON f.community_id = c.id
                LEFT JOIN (
                    SELECT feed_id, COUNT(*) AS comment_count
                    FROM comments
                    GROUP BY feed_id
                ) comment_counts ON comment_counts.feed_id = f.id
                LEFT JOIN (
                    SELECT target_id AS feed_id, COUNT(*) AS like_count
                    FROM likes
                    WHERE target_type = 'FEED'
                    GROUP BY target_id
                ) like_counts ON like_counts.feed_id = f.id
            WHERE f.community_id = :communityId
            ORDER BY f.id DESC
            LIMIT :limit
            """;

    private static final String TITLE_GROUPED_SELECT = """
            SELECT id,
                   title,
                   community_id,
                   community_name,
                   community_job_group,
                   comment_count,
                   like_count
            FROM (
                SELECT
                    f.id   AS id,
                    f.title AS title,
                    c.id   AS community_id,
                    c.name AS community_name,
                    c.job_group AS community_job_group,
                    COALESCE(comment_counts.comment_count, 0) AS comment_count,
                    COALESCE(like_counts.like_count, 0) AS like_count,
                    ROW_NUMBER() OVER (PARTITION BY f.community_id ORDER BY f.id DESC) AS rn
                FROM feeds f
                    JOIN communities c ON f.community_id = c.id
                    LEFT JOIN (
                        SELECT feed_id, COUNT(*) AS comment_count
                        FROM comments
                        GROUP BY feed_id
                    ) comment_counts ON comment_counts.feed_id = f.id
                    LEFT JOIN (
                        SELECT target_id AS feed_id, COUNT(*) AS like_count
                        FROM likes
                        WHERE target_type = 'FEED'
                        GROUP BY target_id
                    ) like_counts ON like_counts.feed_id = f.id
            ) ranked
            WHERE rn <= :limit
            ORDER BY community_id, id DESC
            """;

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<FeedSummary> findRecentSummaries(int limit) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", limit);

        String sql = SUMMARY_SELECT + '\n'
                + DEFAULT_ORDER_BY + '\n'
                + "LIMIT :limit";

        return jdbc.query(sql, params, feedSummaryMapper);
    }

    @Override
    public Optional<FeedSummary> findSummaryById(Long feedId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("feedId", feedId);

        String sql = SUMMARY_SELECT + '\n'
                + "WHERE f.id = :feedId";

        return jdbc.query(sql, params, feedSummaryMapper).stream().findFirst();
    }

    @Override
    public Optional<FeedDetail> findDetailById(Long feedId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("feedId", feedId);

        return jdbc.query(DETAIL_SELECT, params, feedDetailMapper).stream().findFirst();
    }

    @Override
    public List<FeedTitleOnly> findTitleOnlyByCommunity(Long communityId, int limit) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("communityId", communityId)
                .addValue("limit", limit);

        return jdbc.query(TITLE_ONLY_SELECT, params, feedTitleOnlyMapper);
    }

    @Override
    public List<FeedDetail> findTopDetails(int limit) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", limit);

        return jdbc.query(DETAIL_MAIN_SELECT, params, feedDetailMapper);
    }

    @Override
    public List<FeedTitleOnly> findLatestTitlesByCommunity(int limitPerCommunity) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", limitPerCommunity);

        return jdbc.query(TITLE_GROUPED_SELECT, params, feedTitleOnlyMapper);
    }

    @Override
    public List<FeedSummary> findByCommunityIdWithCursor(Long communityId, Long cursorId, int size) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("communityId", communityId)
                .addValue("limit", size)
                .addValue("cursorId", cursorId);

        String sql = SUMMARY_SELECT + '\n'
                + "WHERE f.community_id = :communityId\n"
                + (cursorId != null ? "AND f.id < :cursorId\n" : "")
                + DEFAULT_ORDER_BY + '\n'
                + "LIMIT :limit";

        return jdbc.query(sql, params, feedSummaryMapper);
    }

    private long countFeeds() {
        Long total = jdbc.queryForObject("SELECT COUNT(*) FROM feeds", new MapSqlParameterSource(),
                Long.class);
        return total == null ? 0L : total;
    }

}
