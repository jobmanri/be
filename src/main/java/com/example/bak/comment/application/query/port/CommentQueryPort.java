package com.example.bak.comment.application.query.port;

import com.example.bak.comment.application.query.dto.CommentInfo;
import java.util.List;

public interface CommentQueryPort {

    List<CommentInfo> findByFeedId(Long feedId);
}
