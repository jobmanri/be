package com.example.bak.feedcomment.application.command;

import com.example.bak.feed.application.command.port.FeedCommandPort;
import com.example.bak.feedcomment.application.command.port.FeedCommentCommandPort;
import com.example.bak.feedcomment.application.command.port.UserDataPort;
import com.example.bak.feedcomment.application.command.port.UserDataPort.UserSnapshot;
import com.example.bak.feedcomment.domain.FeedComment;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedCommentCommandService {

    private final FeedCommentCommandPort feedCommentCommandPort;
    private final FeedCommandPort feedCommandPort;
    private final UserDataPort userDataPort;

    public void createComment(Long feedId, String content, Long userId) {
        feedCommandPort.findById(feedId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEED_NOT_FOUND));

        UserSnapshot user = userDataPort.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        FeedComment newComment = FeedComment.create(
                feedId,
                content,
                user.id(),
                user.nickname()
        );

        feedCommentCommandPort.save(newComment);
    }

    public void updateComment(Long commentId, String content, Long userId) {
        FeedComment comment = feedCommentCommandPort.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.isWrittenBy(userId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACTION);
        }

        comment.updateComment(content);
        feedCommentCommandPort.save(comment);
    }
}
