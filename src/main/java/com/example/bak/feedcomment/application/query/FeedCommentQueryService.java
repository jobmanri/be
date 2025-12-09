package com.example.bak.feedcomment.application.query;

import com.example.bak.feedcomment.application.query.dto.CommentInfo;
import com.example.bak.feedcomment.application.query.port.FeedCommentQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedCommentQueryService {

    private final FeedCommentQueryPort feedCommentQueryPort;

    public List<CommentInfo> getComments(Long feedId) {
        return feedCommentQueryPort.findByFeedId(feedId);
    }
}