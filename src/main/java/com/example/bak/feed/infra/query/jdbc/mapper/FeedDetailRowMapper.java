package com.example.bak.feed.infra.query.jdbc.mapper;

import com.example.bak.community.application.query.dto.CommunityResult;
import com.example.bak.feed.application.query.dto.FeedDetail;
import com.example.bak.user.application.dto.UserInfo;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class FeedDetailRowMapper implements RowMapper<FeedDetail> {

    @Override
    public FeedDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserInfo author = new UserInfo(
                rs.getLong("author_id"),
                rs.getString("author_nickname")
        );

        CommunityResult.Detail community = new CommunityResult.Detail(
                rs.getLong("community_id"),
                rs.getString("community_name"),
                rs.getString("community_job_group")
        );

        return new FeedDetail(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("content"),
                author,
                community
        );
    }
}
