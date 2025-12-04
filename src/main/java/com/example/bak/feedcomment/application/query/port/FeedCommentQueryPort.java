package com.example.bak.feedcomment.application.query.port;

import com.example.bak.feedcomment.application.query.dto.CommentInfo;
import java.util.List;

public interface FeedCommentQueryPort {

    List<CommentInfo> findByFeedId(Long feedId);
}

