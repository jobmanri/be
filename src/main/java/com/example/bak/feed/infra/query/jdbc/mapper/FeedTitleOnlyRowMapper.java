package com.example.bak.feed.infra.query.jdbc.mapper;

import com.example.bak.community.application.query.dto.CommunityResult;
import com.example.bak.feed.application.query.dto.FeedTitleOnly;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class FeedTitleOnlyRowMapper implements RowMapper<FeedTitleOnly> {

    @Override
    public FeedTitleOnly mapRow(ResultSet rs, int rowNum) throws SQLException {
        CommunityResult.Detail community = new CommunityResult.Detail(
                rs.getLong("community_id"),
                rs.getString("community_name"),
                rs.getString("community_job_group")
        );

        return new FeedTitleOnly(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getInt("like_count"),
                rs.getInt("comment_count"),
                community
        );
    }
}
