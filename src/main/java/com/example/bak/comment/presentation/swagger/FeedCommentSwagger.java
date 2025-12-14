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
            description = "특정 피드에 인증된 사용자가 새로운 댓글을 작성합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "댓글 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "CommentCreateSuccess",
                                    summary = "댓글 생성 성공",
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
                    responseCode = "400",
                    description = "잘못된 요청 - 댓글 본문 누락",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "CommentCreateBadRequest",
                                    summary = "댓글 내용 없음",
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "댓글 내용은 필수입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "CommentCreateUnauthorized",
                                    summary = "토큰 누락",
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "토큰이 없습니다.",
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
                                    name = "CommentCreateFeedNotFound",
                                    summary = "댓글 대상 피드 없음",
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "피드 리소스를 찾을 수 없습니다.",
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
                                    name = "CommentCreateRequest",
                                    summary = "댓글 생성 요청",
                                    value = """
                                            {
                                                "content": "좋은 정보 감사합니다!"
                                            }
                                            """
                            )
                    )
            )
            @RequestBody CommentRequest request,
            @Parameter(hidden = true, description = "인증된 사용자 ID", required = true)
            Long userId
    );

    @Operation(
            summary = "댓글 목록 조회",
            description = "특정 피드에 작성된 모든 댓글을 최신순으로 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "댓글 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "CommentList",
                                    summary = "댓글 목록 응답",
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "댓글을 성공적으로 조회하였습니다.",
                                                "data": [
                                                    {
                                                        "id": 1,
                                                        "authorId": 5,
                                                        "authorName": "infra-cat",
                                                        "content": "좋은 정보 감사합니다!"
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
                                    name = "CommentListFeedNotFound",
                                    summary = "피드 미존재",
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "피드 리소스를 찾을 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> getComments(
            @Parameter(description = "피드 ID", required = true, example = "1")
            @PathVariable Long feedId
    );

    @Operation(
            summary = "댓글 수정",
            description = "댓글 작성자가 본인의 댓글 본문을 수정합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "댓글 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "CommentUpdateSuccess",
                                    summary = "수정 성공",
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
                    responseCode = "400",
                    description = "잘못된 요청 - 본문 누락",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "CommentUpdateBadRequest",
                                    summary = "본문 없음",
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "댓글 내용은 필수입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "CommentUpdateUnauthorized",
                                    summary = "토큰 없음",
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "토큰이 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "권한 없음 - 작성자 불일치",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "CommentUpdateForbidden",
                                    summary = "다른 사용자가 수정 시도",
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
                                    name = "CommentUpdateNotFound",
                                    summary = "댓글 미존재",
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "댓글 리소스를 찾을 수 없습니다.",
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
                                    name = "CommentUpdateRequest",
                                    summary = "댓글 수정 요청",
                                    value = """
                                            {
                                                "content": "수정된 댓글 내용입니다."
                                            }
                                            """
                            )
                    )
            )
            @RequestBody CommentRequest request,
            @Parameter(hidden = true, description = "인증된 사용자 ID", required = true)
            Long userId
    );
}
