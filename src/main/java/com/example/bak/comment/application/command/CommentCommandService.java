package com.example.bak.comment.application.command;

import com.example.bak.comment.application.command.port.CommentCommandPort;
import com.example.bak.comment.application.command.port.ProfileDataPort;
import com.example.bak.comment.domain.Comment;
import com.example.bak.comment.domain.ProfileSnapShot;
import com.example.bak.feed.application.command.port.FeedCommandPort;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentCommandService {

    private final CommentCommandPort commentCommandPort;
    private final FeedCommandPort feedCommandPort;
    private final ProfileDataPort profileDataPort;

    public void createComment(Long feedId, String content, Long userId) {
        feedCommandPort.findById(feedId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEED_NOT_FOUND));

        ProfileSnapShot userProfile = profileDataPort.findSnapshotByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Comment newComment = Comment.create(
                feedId,
                content,
                userProfile.userId(),
                userProfile.nickname()
        );

        commentCommandPort.save(newComment);
    }

    public void updateComment(Long commentId, String content, Long userId) {
        Comment comment = commentCommandPort.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.isWrittenBy(userId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACTION);
        }

        comment.updateComment(content);
        commentCommandPort.save(comment);
    }
}
