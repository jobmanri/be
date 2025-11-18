package com.example.bak.feedcomment.application;

import com.example.bak.feed.domain.Feed;
import com.example.bak.feed.domain.FeedRepository;
import com.example.bak.feedcomment.application.dto.CommentInfo;
import com.example.bak.feedcomment.domain.FeedComment;
import com.example.bak.feedcomment.domain.FeedCommentRepository;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.user.domain.User;
import com.example.bak.user.domain.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedCommentService {

    private final FeedCommentRepository commentRepository;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createComment(Long feedId, String content, Long userId) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEED_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        FeedComment newComment = FeedComment.create(content, user);

        feed.addComment(newComment);
        commentRepository.save(newComment);
    }

    @Transactional
    public void updateComment(Long commentId, String content, Long userId) {
        FeedComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));

        if (!isAuthor(comment, userId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACTION);
        }

        comment.updateComment(content);
    }

    public List<CommentInfo> getComments(Long feedId) {
        return commentRepository.findByFeedId(feedId).stream()
                .map(CommentInfo::from)
                .toList();
    }

    private boolean isAuthor(FeedComment comment, Long userId) {
        return comment.getAuthor().getId().equals(userId);
    }
}
