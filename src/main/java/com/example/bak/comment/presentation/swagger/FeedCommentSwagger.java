package com.example.bak.comment.presentation.swagger;

import com.example.bak.comment.presentation.dto.CommentRequest;
import com.example.bak.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "FeedComment", description = "피드 댓글 관리 API")
public interface FeedCommentSwagger {

    @Operation(
            summary = "댓글 생성",
            description = "특정 피드에 새로운 댓글을 작성합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "댓글 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "댓글을 성공적으로 생성하였습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "피드를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "피드를 찾을 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> createComment(
            @Parameter(description = "피드 ID", required = true, example = "1")
            @PathVariable Long feedId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "댓글 생성 요청 정보",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CommentRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "content": "좋은 정보 감사합니다!",
                                                "userId": 1
                                            }
                                            """
                            )
                    )
            )
            @RequestBody CommentRequest request
    );

    @Operation(
            summary = "댓글 목록 조회",
            description = "특정 피드의 모든 댓글을 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "댓글 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "댓글을 성공적으로 조회하였습니다.",
                                                "data": [
                                                    {
                                                        "commentId": 1,
                                                        "content": "좋은 정보 감사합니다!",
                                                        "authorId": 1,
                                                        "authorName": "홍길동",
                                                        "createdAt": "2024-01-15T11:00:00",
                                                        "updatedAt": "2024-01-15T11:00:00"
                                                    }
                                                ]
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "피드를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "피드를 찾을 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> getComment(
            @Parameter(description = "피드 ID", required = true, example = "1")
            @PathVariable Long feedId
    );

    @Operation(
            summary = "댓글 수정",
            description = "특정 댓글의 내용을 수정합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "댓글 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "댓글을 성공적으로 수정하였습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "권한 없음 - 댓글 작성자만 수정 가능",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "댓글을 수정할 권한이 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "댓글을 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "댓글을 찾을 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> updateComment(
            @Parameter(description = "댓글 ID", required = true, example = "1")
            @PathVariable Long commentId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "댓글 수정 요청 정보",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CommentRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "content": "수정된 댓글 내용입니다.",
                                                "userId": 1
                                            }
                                            """
                            )
                    )
            )
            @RequestBody CommentRequest request
    );
}
