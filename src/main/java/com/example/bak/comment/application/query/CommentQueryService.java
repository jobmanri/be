package com.example.bak.comment.application.query;

import com.example.bak.comment.application.query.dto.CommentInfo;
import com.example.bak.comment.application.query.port.CommentQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryService {

    private final CommentQueryPort commentQueryPort;

    public List<CommentInfo> getComments(Long feedId) {
        return commentQueryPort.findByFeedId(feedId);
    }
}
