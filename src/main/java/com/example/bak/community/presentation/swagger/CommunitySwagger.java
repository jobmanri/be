package com.example.bak.community.presentation.swagger;

import com.example.bak.community.presentation.dto.CommunityRequest;
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

@Tag(name = "Community", description = "커뮤니티 관리 API")
public interface CommunitySwagger {

    @Operation(
            summary = "커뮤니티 목록 조회",
            description = "특정 회사의 모든 커뮤니티 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "커뮤니티 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "커뮤니티 목록을 성공적으로 불러왔습니다.",
                                                "data": [
                                                    {
                                                        "communityId": 1,
                                                        "name": "백엔드 개발자",
                                                        "jobGroup": "개발",
                                                        "companyId": 1,
                                                        "memberCount": 50
                                                    }
                                                ]
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "회사를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "회사를 찾을 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> getCommunities(
            @Parameter(description = "회사 ID", required = true, example = "1")
            @PathVariable Long companyId
    );

    @Operation(
            summary = "커뮤니티 생성",
            description = "특정 회사에 새로운 커뮤니티를 생성합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "커뮤니티 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "커뮤니티 정보를 성공적으로 저장했습니다.",
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
                    description = "회사를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "회사를 찾을 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> createCommunity(
            @Parameter(description = "회사 ID", required = true, example = "1")
            @PathVariable Long companyId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "커뮤니티 생성 요청 정보",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CommunityRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "name": "백엔드 개발자",
                                                "jobGroup": "개발"
                                            }
                                            """
                            )
                    )
            )
            @RequestBody CommunityRequest request
    );

    @Operation(
            summary = "커뮤니티 수정",
            description = "특정 커뮤니티의 정보를 수정합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "커뮤니티 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "커뮤니티 정보를 성공적으로 수정했습니다.",
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
    ResponseEntity<ApiResponse> updateCompanyCommunity(
            @Parameter(description = "커뮤니티 ID", required = true, example = "1")
            @PathVariable Long communityId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "커뮤니티 수정 요청 정보",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CommunityRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "name": "프론트엔드 개발자",
                                                "jobGroup": "개발"
                                            }
                                            """
                            )
                    )
            )
            @RequestBody CommunityRequest request
    );

    @Operation(
            summary = "커뮤니티 삭제",
            description = "특정 커뮤니티를 삭제합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "커뮤니티 삭제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "회사 정보를 성공적으로 삭제했습니다.",
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
    ResponseEntity<ApiResponse> deleteCommunityCompany(
            @Parameter(description = "커뮤니티 ID", required = true, example = "1")
            @PathVariable Long communityId
    );
}
