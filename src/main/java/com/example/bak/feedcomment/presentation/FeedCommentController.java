package com.example.bak.feedcomment.presentation;

import com.example.bak.feedcomment.application.FeedCommentService;
import com.example.bak.feedcomment.application.dto.CommentInfo;
import com.example.bak.feedcomment.presentation.dto.CommentRequest;
import com.example.bak.global.common.response.ApiResponse;
import com.example.bak.global.common.response.ApiResponseFactory;
import com.example.bak.global.common.utils.UriUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
public class FeedCommentController {

    private final FeedCommentService feedCommentService;

    @PostMapping("/feeds/{feedId}/comments")
    public ResponseEntity<ApiResponse> createComment(
            @PathVariable Long feedId,
            @RequestBody CommentRequest request
    ) {
        feedCommentService.createComment(
                feedId,
                request.content(),
                request.userId()
        );
        ApiResponse response = ApiResponseFactory.successVoid("댓글을 성공적으로 생성하였습니다.");
        return ResponseEntity.created(UriUtils.current())
                .body(response);
    }

    @GetMapping("/feeds/{feedId}/comments")
    public ResponseEntity<ApiResponse> getComment(@PathVariable Long feedId) {
        List<CommentInfo> comments = feedCommentService.getComments(feedId);
        ApiResponse response = ApiResponseFactory.success("댓글을 성공적으로 조회하였습니다.", comments);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequest request
    ) {
        feedCommentService.updateComment(commentId, request.content(), request.userId());
        ApiResponse response = ApiResponseFactory.successVoid("댓글을 성공적으로 수정하였습니다.");
        return ResponseEntity.ok(response);
    }
}
