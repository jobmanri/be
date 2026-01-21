package com.example.bak.comment.presentation;

import com.example.bak.comment.application.command.CommentCommandService;
import com.example.bak.comment.application.query.CommentQueryService;
import com.example.bak.comment.application.query.dto.CommentInfo;
import com.example.bak.comment.presentation.dto.CommentRequest;
import com.example.bak.comment.presentation.swagger.FeedCommentSwagger;
import com.example.bak.global.common.response.ApiResponse;
import com.example.bak.global.common.response.ApiResponseFactory;
import com.example.bak.global.common.utils.UriUtils;
import com.example.bak.global.security.annotation.AuthUser;
import com.example.bak.like.application.command.LikeCommandService;
import com.example.bak.like.domain.LikeTargetType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController implements FeedCommentSwagger {

    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;
    private final LikeCommandService likeCommandService;

    @Override
    @PostMapping("/feeds/{feedId}/comments")
    public ResponseEntity<ApiResponse> createComment(
            @PathVariable Long feedId,
            @RequestBody CommentRequest request,
            @AuthUser Long userId
    ) {
        commentCommandService.createComment(
                feedId,
                request.content(),
                userId
        );
        ApiResponse response = ApiResponseFactory.successVoid("댓글을 성공적으로 생성하였습니다.");
        return ResponseEntity.created(UriUtils.current())
                .body(response);
    }

    @Override
    @GetMapping("/feeds/{feedId}/comments")
    public ResponseEntity<ApiResponse> getComments(@PathVariable Long feedId) {
        List<CommentInfo> comments = commentQueryService.getComments(feedId);
        ApiResponse response = ApiResponseFactory.success("댓글을 성공적으로 조회하였습니다.", comments);
        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequest request,
            @AuthUser Long userId
    ) {
        commentCommandService.updateComment(commentId, request.content(), userId);
        ApiResponse response = ApiResponseFactory.successVoid("댓글을 성공적으로 수정하였습니다.");
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/comments/{commentId}/likes")
    public ResponseEntity<ApiResponse> likeComment(
            @AuthUser Long userId,
            @PathVariable Long commentId
    ) {
        likeCommandService.like(LikeTargetType.COMMENT, commentId, userId);
        ApiResponse response = ApiResponseFactory.successVoid("댓글에 좋아요를 추가했습니다.");
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/comments/{commentId}/likes")
    public ResponseEntity<ApiResponse> unlikeComment(
            @AuthUser Long userId,
            @PathVariable Long commentId
    ) {
        likeCommandService.unlike(LikeTargetType.COMMENT, commentId, userId);
        ApiResponse response = ApiResponseFactory.successVoid("댓글 좋아요를 취소했습니다.");
        return ResponseEntity.ok(response);
    }
}
