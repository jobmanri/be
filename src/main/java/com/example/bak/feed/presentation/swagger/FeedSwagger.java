package com.example.bak.feed.presentation.swagger;

import com.example.bak.feed.presentation.dto.FeedRequest;
import com.example.bak.feed.presentation.dto.FeedUpdateRequest;
import com.example.bak.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Feed", description = "피드 관리 API")
public interface FeedSwagger {

    @Operation(
            summary = "피드 생성",
            description = "인증된 사용자가 제목, 내용, 커뮤니티 정보를 입력하여 새 피드를 생성합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "피드 생성 성공",
                    headers = @Header(name = "Location", description = "생성된 피드 상세 조회 URI (/api/v1/feeds/{feedId})"),
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "FeedCreateSuccess",
                                    summary = "피드 생성 성공 응답",
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "피드를 성공적으로 생성하였습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 - 필수 입력 누락 또는 형식 오류",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "FeedCreateInvalidRequest",
                                    summary = "필수 값 누락 시 응답",
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "제목은 필수 입력입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패 - 토큰이 없거나 만료됨",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "FeedCreateUnauthorized",
                                    summary = "토큰 누락 시 응답",
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
                    description = "커뮤니티를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "FeedCreateCommunityNotFound",
                                    summary = "커뮤니티가 존재하지 않을 때",
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "커뮤니티 리소스를 찾을 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> createFeed(
            @Parameter(hidden = true, description = "AccessToken으로 식별된 인증 사용자 ID", required = true)
            Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "피드 생성 요청 정보",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = FeedRequest.class),
                            examples = @ExampleObject(
                                    name = "FeedCreateRequest",
                                    summary = "피드 생성 요청",
                                    value = """
                                            {
                                                "title": "신입 백엔드 개발자 채용 정보 공유",
                                                "content": "우리 팀에서 진행 중인 채용 공고와 준비 TIP을 공유합니다.",
                                                "communityId": 1
                                            }
                                            """
                            )
                    )
            )
            @RequestBody FeedRequest request
    );

    @Operation(
            summary = "피드 목록 조회",
            description = "페이지 번호와 페이지 크기를 지정해 최신 피드 목록을 조회하며, 각 항목은 본문 미리보기와 좋아요/댓글 수를 함께 제공합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "피드 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "FeedList",
                                    summary = "피드 목록 응답",
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "피드 목록을 성공적으로 조회하였습니다.",
                                                "data": [
                                                    {
                                                        "id": 7,
                                                        "title": "Infra 스터디 회고",
                                                        "contentPreview": "스터디에서 다룬 주제와 배운 내용을 간단히 정리했습니다...",
                                                        "author": {
                                                            "id": 21,
                                                            "nickname": "infra-cat"
                                                        },
                                                        "community": {
                                                            "id": 3,
                                                            "name": "백엔드 개발자",
                                                            "jobGroup": "개발"
                                                        },
                                                        "likeCount": 0,
                                                        "commentCount": 2
                                                    }
                                                ]
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 - page 또는 size 값 오류",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "FeedListBadRequest",
                                    summary = "page/size 값이 음수일 때",
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "페이지 번호와 크기는 음수가 될 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> getFeeds(
            @Parameter(description = "조회할 개수", example = "20",
                    schema = @Schema(minimum = "1", defaultValue = "20"))
            @RequestParam(defaultValue = "20") int limit
    );

    @Operation(
            summary = "메인 페이지 피드 조회",
            description = "최신 순으로 정렬된 피드 상세 정보를 제한 개수만큼 조회합니다."
    )
    ResponseEntity<ApiResponse> getMainPageFeeds(
            @Parameter(description = "조회할 개수", example = "10",
                    schema = @Schema(minimum = "1", defaultValue = "10"))
            @RequestParam(defaultValue = "10") int limit
    );

    @Operation(
            summary = "메인 페이지 커뮤니티별 제목 조회",
            description = "각 커뮤니티별로 최신 N개의 피드 제목만 묶어서 조회합니다."
    )
    ResponseEntity<ApiResponse> getMainPageTitles(
            @Parameter(description = "커뮤니티별 조회할 개수", example = "5",
                    schema = @Schema(minimum = "1", defaultValue = "5"))
            @RequestParam(defaultValue = "5") int limit
    );

    @Operation(
            summary = "커뮤니티 피드 목록 조회",
            description = "특정 커뮤니티에 속한 피드를 페이지네이션 형태로 조회합니다. 응답에는 nextCursor와 hasNext가 포함됩니다."
    )
    ResponseEntity<ApiResponse> getFeedsByCommunity(
            @Parameter(description = "커뮤니티 ID", required = true, example = "1")
            @PathVariable Long communityId,
            @Parameter(description = "다음 페이지 조회를 위한 커서(이전 응답의 nextCursor)")
            @RequestParam(required = false) Long cursor,
            @Parameter(description = "한 번에 가져올 개수", example = "20",
                    schema = @Schema(minimum = "1", defaultValue = "20"))
            @RequestParam(defaultValue = "20") int size
    );

    @Operation(
            summary = "커뮤니티별 피드 제목 조회",
            description = "특정 커뮤니티에서 최신 N개의 피드 제목과 좋아요/댓글 요약을 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "커뮤니티 피드 제목 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "FeedTitleList",
                                    summary = "커뮤니티별 제목 리스트",
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "커뮤니티 피드 제목을 성공적으로 조회하였습니다.",
                                                "data": [
                                                    {
                                                        "id": 10,
                                                        "title": "블라인드 스타일 데이터",
                                                        "likeCount": 12,
                                                        "commentCount": 4,
                                                        "community": {
                                                            "id": 3,
                                                            "name": "백엔드 개발자",
                                                            "jobGroup": "개발"
                                                        }
                                                    }
                                                ]
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> getFeedTitlesByCommunity(
            @Parameter(description = "커뮤니티 ID", required = true, example = "1")
            @PathVariable Long communityId,
            @Parameter(description = "조회할 개수", example = "5",
                    schema = @Schema(minimum = "1", defaultValue = "5"))
            @RequestParam(defaultValue = "5") int limit
    );

    @Operation(
            summary = "피드 요약 조회",
            description = "피드 카드 노출용으로 필요한 최소 정보(제목, 본문 미리보기, 작성자, 커뮤니티, 좋아요/댓글 수)를 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "피드 요약 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "FeedSummary",
                                    summary = "요약 응답",
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "피드 요약을 성공적으로 조회하였습니다.",
                                                "data": {
                                                    "id": 1,
                                                    "title": "신입 개발자 채용 정보 공유",
                                                    "contentPreview": "채용 절차를 준비하면서 도움이 되었던 자료를 정리했습니다...",
                                                    "author": {
                                                        "id": 5,
                                                        "nickname": "backend-dev"
                                                    },
                                                    "community": {
                                                        "id": 2,
                                                        "name": "백엔드 개발자",
                                                        "jobGroup": "개발"
                                                    },
                                                    "likeCount": 0,
                                                    "commentCount": 5
                                                }
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
                                    name = "FeedSummaryNotFound",
                                    summary = "요약 대상 피드 없음",
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
    ResponseEntity<ApiResponse> getFeedSummary(
            @Parameter(description = "피드 ID", required = true, example = "1")
            @PathVariable Long feedId
    );

    @Operation(
            summary = "피드 상세 조회",
            description = "본문과 커뮤니티 정보까지 포함한 피드 전체 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "피드 상세 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "FeedDetail",
                                    summary = "상세 응답",
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "피드 상세를 성공적으로 조회하였습니다.",
                                                "data": {
                                                    "id": 1,
                                                    "title": "신입 개발자 채용 정보 공유",
                                                    "content": "채용 절차와 준비 Tip을 정리했습니다.",
                                                    "author": {
                                                        "id": 5,
                                                        "nickname": "backend-dev"
                                                    },
                                                    "community": {
                                                        "id": 2,
                                                        "name": "백엔드 개발자",
                                                        "jobGroup": "개발"
                                                    },
                                                    "likeCount": 0,
                                                    "commentCount": 5
                                                }
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
                                    name = "FeedDetailNotFound",
                                    summary = "존재하지 않는 피드",
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
    ResponseEntity<ApiResponse> getFeedDetail(
            @Parameter(description = "피드 ID", required = true, example = "1")
            @PathVariable Long feedId
    );

    @Operation(
            summary = "피드 수정",
            description = "작성자가 본인이 작성한 피드의 제목과 내용을 수정합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "피드 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "FeedUpdateSuccess",
                                    summary = "수정 성공 응답",
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "피드를 성공적으로 수정하였습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 - 수정 값 누락",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "FeedUpdateBadRequest",
                                    summary = "수정 본문 없음",
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "수정할 제목 또는 내용이 필요합니다.",
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
                                    name = "FeedUpdateUnauthorized",
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
                    description = "작성자가 아닌 경우",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "FeedUpdateForbidden",
                                    summary = "다른 사용자의 피드 수정",
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "권한이 없습니다.",
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
                                    name = "FeedUpdateNotFound",
                                    summary = "수정 대상 없음",
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
    ResponseEntity<ApiResponse> updateFeed(
            @Parameter(hidden = true, description = "인증된 사용자 ID", required = true)
            Long userId,
            @Parameter(description = "피드 ID", required = true, example = "1")
            @PathVariable Long feedId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "피드 수정 요청 정보",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = FeedUpdateRequest.class),
                            examples = @ExampleObject(
                                    name = "FeedUpdateRequest",
                                    summary = "피드 수정 요청",
                                    value = """
                                            {
                                                "title": "제목을 업데이트합니다",
                                                "content": "본문을 최신 정보로 업데이트합니다."
                                            }
                                            """
                            )
                    )
            )
            @RequestBody FeedUpdateRequest request
    );

    @Operation(
            summary = "피드 삭제",
            description = "작성자가 본인이 작성한 피드를 영구 삭제합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "피드 삭제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "FeedDeleteSuccess",
                                    summary = "삭제 성공 응답",
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "피드를 성공적으로 삭제하였습니다.",
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
                                    name = "FeedDeleteUnauthorized",
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
                    responseCode = "403",
                    description = "작성자가 아닌 경우",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "FeedDeleteForbidden",
                                    summary = "다른 사용자의 피드 삭제",
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "권한이 없습니다.",
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
                                    name = "FeedDeleteNotFound",
                                    summary = "삭제 대상 없음",
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
    ResponseEntity<ApiResponse> deleteFeed(
            @Parameter(hidden = true, description = "인증된 사용자 ID", required = true)
            Long userId,
            @Parameter(description = "피드 ID", required = true, example = "1")
            @PathVariable Long feedId
    );

    @Operation(
            summary = "피드 좋아요 등록",
            description = "로그인 사용자가 특정 피드에 좋아요를 추가합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "좋아요 등록 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "FeedLikeSuccess",
                                    summary = "좋아요 성공",
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "피드에 좋아요를 추가했습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> likeFeed(
            @Parameter(hidden = true, description = "인증된 사용자 ID", required = true)
            Long userId,
            @Parameter(description = "피드 ID", required = true, example = "1")
            @PathVariable Long feedId
    );

    @Operation(
            summary = "피드 좋아요 취소",
            description = "로그인 사용자가 자신이 누른 좋아요를 취소합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "좋아요 취소 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "FeedUnlikeSuccess",
                                    summary = "좋아요 취소",
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "피드 좋아요를 취소했습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> unlikeFeed(
            @Parameter(hidden = true, description = "인증된 사용자 ID", required = true)
            Long userId,
            @Parameter(description = "피드 ID", required = true, example = "1")
            @PathVariable Long feedId
    );
}
