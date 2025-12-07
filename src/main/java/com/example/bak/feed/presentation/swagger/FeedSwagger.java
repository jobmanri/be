package com.example.bak.feed.presentation.swagger;

import com.example.bak.feed.presentation.dto.FeedRequest;
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
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Feed", description = "피드 관리 API")
public interface FeedSwagger {

    @Operation(
            summary = "피드 생성",
            description = "새로운 피드를 생성합니다. 제목, 내용, 커뮤니티 ID, 사용자 ID를 입력받습니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "피드 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
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
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "유효하지 않은 요청입니다.",
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
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "커뮤니티를 찾을 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> createFeed(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "피드 생성 요청 정보",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = FeedRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "title": "신입 개발자 채용 정보 공유",
                                                "content": "우리 회사에서 신입 개발자를 채용합니다...",
                                                "communityId": 1,
                                                "userId": 1
                                            }
                                            """
                            )
                    )
            )
            @RequestBody FeedRequest request
    );

    @Operation(
            summary = "피드 목록 조회",
            description = "페이지네이션을 적용하여 피드 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "피드 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "피드 목록을 성공적으로 조회하였습니다.",
                                                "data": [
                                                    {
                                                        "feedId": 1,
                                                        "title": "신입 개발자 채용 정보 공유",
                                                        "authorName": "홍길동",
                                                        "communityName": "백엔드 개발자",
                                                        "createdAt": "2024-01-15T10:30:00",
                                                        "commentCount": 5,
                                                        "likeCount": 10
                                                    }
                                                ]
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> getFeeds(
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "10")
            @RequestParam(defaultValue = "10") int size
    );

    @Operation(
            summary = "피드 요약 조회",
            description = "특정 피드의 요약 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "피드 요약 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "피드 요약을 성공적으로 조회하였습니다.",
                                                "data": {
                                                    "feedId": 1,
                                                    "title": "신입 개발자 채용 정보 공유",
                                                    "authorName": "홍길동",
                                                    "communityName": "백엔드 개발자",
                                                    "createdAt": "2024-01-15T10:30:00",
                                                    "commentCount": 5,
                                                    "likeCount": 10
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
    ResponseEntity<ApiResponse> getFeedSummary(
            @Parameter(description = "피드 ID", required = true, example = "1")
            @PathVariable Long feedId
    );

    @Operation(
            summary = "피드 상세 조회",
            description = "특정 피드의 상세 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "피드 상세 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "피드 상세를 성공적으로 조회하였습니다.",
                                                "data": {
                                                    "feedId": 1,
                                                    "title": "신입 개발자 채용 정보 공유",
                                                    "content": "우리 회사에서 신입 개발자를 채용합니다...",
                                                    "authorId": 1,
                                                    "authorName": "홍길동",
                                                    "communityId": 1,
                                                    "communityName": "백엔드 개발자",
                                                    "createdAt": "2024-01-15T10:30:00",
                                                    "updatedAt": "2024-01-15T10:30:00",
                                                    "commentCount": 5,
                                                    "likeCount": 10
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
    ResponseEntity<ApiResponse> getFeedDetail(
            @Parameter(description = "피드 ID", required = true, example = "1")
            @PathVariable Long feedId
    );
}
