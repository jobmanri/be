package com.example.bak.feed.presentation;

import com.example.bak.feed.application.CommentService;
import com.example.bak.feed.application.dto.CommentInfo;
import com.example.bak.feed.application.dto.CommentResult;
import com.example.bak.feed.presentation.dto.CommentRequest;
import com.example.bak.global.common.response.ApiResponse;
import com.example.bak.global.common.response.ApiResponseFactory;
import com.example.bak.global.common.utils.UriUtils;
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
@RequestMapping("api/v1/feeds")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{feedId}/comments")
    public ResponseEntity<ApiResponse> createComment(
            @PathVariable Long feedId,
            @RequestBody CommentRequest request
    ) {
        CommentResult result = commentService.createComment(
                feedId,
                request.content(),
                request.userId()
        );
        ApiResponse response = ApiResponseFactory.successVoid("댓글을 성공적으로 생성하였습니다.");
        return ResponseEntity.created(UriUtils.current(result.id()))
                .body(response);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequest request
    ) {
        commentService.updateComment(commentId, request.content(), request.userId());
        ApiResponse response = ApiResponseFactory.successVoid("댓글을 성공적으로 수정하였습니다.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> getComment(@PathVariable Long commentId) {
        CommentInfo comment = commentService.getComment(commentId);
        ApiResponse response = ApiResponseFactory.success("댓글을 성공적으로 조회하였습니다.", comment);
        return ResponseEntity.ok(response);
    }
}
