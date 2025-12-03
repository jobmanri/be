package com.example.bak.feedcomment.application.query;

import com.example.bak.feedcomment.application.query.dto.CommentInfo;
import com.example.bak.feedcomment.domain.FeedCommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedCommentQueryService {

    private final FeedCommentRepository commentRepository;

    public List<CommentInfo> getComments(Long feedId) {
        return commentRepository.findByFeedId(feedId).stream()
                .map(CommentInfo::from)
                .toList();
    }
}

