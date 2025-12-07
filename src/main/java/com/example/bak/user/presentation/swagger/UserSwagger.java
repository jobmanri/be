package com.example.bak.user.presentation.swagger;

import com.example.bak.global.common.response.ApiResponse;
import com.example.bak.user.presentation.dto.ProfileRequest;
import com.example.bak.user.presentation.dto.UserRequest;
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

@Tag(name = "User", description = "사용자 관리 API")
public interface UserSwagger {

    @Operation(
            summary = "사용자 생성",
            description = "새로운 사용자를 생성합니다. 이메일과 비밀번호를 입력받아 사용자를 등록합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "사용자 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "사용자를 성공적으로 생성하였습니다.",
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
                                                "message": "유효하지 않은 이메일 형식입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "사용자 생성 요청 정보",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UserRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "email": "user@example.com",
                                                "password": "password123!"
                                            }
                                            """
                            )
                    )
            )
            @RequestBody UserRequest request
    );

    @Operation(
            summary = "사용자 프로필 조회",
            description = "사용자 ID로 프로필 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "프로필 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "프로필 정보를 성공하였습니다.",
                                                "data": {
                                                    "userId": 1,
                                                    "name": "홍길동",
                                                    "nickname": "gildong",
                                                    "email": "user@example.com"
                                                }
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "사용자를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "사용자를 찾을 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> getUserProfile(
            @Parameter(description = "사용자 ID", required = true, example = "1")
            @PathVariable Long userId
    );

    @Operation(
            summary = "프로필 생성",
            description = "인증된 사용자의 프로필을 생성합니다. 이름과 닉네임을 입력받습니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "프로필 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "프로필을 생성하였습니다.",
                                                "data": {
                                                    "userId": 1,
                                                    "name": "홍길동",
                                                    "nickname": "gildong"
                                                }
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
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "인증이 필요합니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> createProfile(
            @Parameter(hidden = true) Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "프로필 생성 요청 정보",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ProfileRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "name": "홍길동",
                                                "nickname": "gildong"
                                            }
                                            """
                            )
                    )
            )
            @RequestBody ProfileRequest request
    );
}
