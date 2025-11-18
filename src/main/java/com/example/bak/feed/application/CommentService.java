package com.example.bak.feed.application;

import com.example.bak.feed.application.dto.CommentInfo;
import com.example.bak.feed.application.dto.CommentResult;
import com.example.bak.feed.domain.Feed;
import com.example.bak.feed.domain.FeedComment;
import com.example.bak.feed.domain.FeedCommentRepository;
import com.example.bak.feed.domain.FeedRepository;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.user.domain.User;
import com.example.bak.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final FeedCommentRepository commentRepository;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResult createComment(Long feedId, String content, Long userId) {
        Feed feed = feedRepository.findFeedById(feedId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEED_NOT_FOUND));

        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        FeedComment newComment = FeedComment.create(content, user);
        feed.addComment(newComment);
        FeedComment savedComment = commentRepository.save(newComment);

        return CommentResult.of(savedComment.getId());
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

    public CommentInfo getComment(Long commentId) {
        FeedComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));

        return CommentInfo.from(comment);
    }

    private boolean isAuthor(FeedComment comment, Long userId) {
        return comment.getAuthor().getId().equals(userId);
    }
}
