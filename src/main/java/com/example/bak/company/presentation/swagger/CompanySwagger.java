package com.example.bak.company.presentation.swagger;

import com.example.bak.company.presentation.dto.CompanyRequest;
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

@Tag(name = "Company", description = "회사 관리 API")
public interface CompanySwagger {

    @Operation(
            summary = "회사 목록 조회",
            description = "등록된 모든 회사의 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "회사 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "회사 목록을 성공적으로 조회했습니다.",
                                                "data": [
                                                    {
                                                        "companyId": 1,
                                                        "name": "테크 컴퍼니",
                                                        "careerLink": "https://career.techcompany.com",
                                                        "logoUrl": "https://logo.techcompany.com/logo.png",
                                                        "description": "혁신적인 기술 회사입니다."
                                                    }
                                                ]
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> getCompanies();

    @Operation(
            summary = "회사 상세 조회",
            description = "특정 회사의 상세 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "회사 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "회사 정보를 성공적으로 조회했습니다.",
                                                "data": {
                                                    "companyId": 1,
                                                    "name": "테크 컴퍼니",
                                                    "careerLink": "https://career.techcompany.com",
                                                    "logoUrl": "https://logo.techcompany.com/logo.png",
                                                    "description": "혁신적인 기술 회사입니다.",
                                                    "communityCount": 5
                                                }
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
    ResponseEntity<ApiResponse> getCompany(
            @Parameter(description = "회사 ID", required = true, example = "1")
            @PathVariable Long companyId
    );

    @Operation(
            summary = "회사 생성",
            description = "새로운 회사를 등록합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "회사 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "회사 정보를 성공적으로 생성했습니다.",
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
                    responseCode = "409",
                    description = "이미 존재하는 회사",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "이미 존재하는 회사입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> createCompany(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "회사 생성 요청 정보",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CompanyRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "name": "테크 컴퍼니",
                                                "careerLink": "https://career.techcompany.com",
                                                "logoUrl": "https://logo.techcompany.com/logo.png",
                                                "description": "혁신적인 기술 회사입니다."
                                            }
                                            """
                            )
                    )
            )
            @RequestBody CompanyRequest request
    );

    @Operation(
            summary = "회사 수정",
            description = "특정 회사의 정보를 수정합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "회사 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "회사 정보를 성공적으로 수정했습니다.",
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
    ResponseEntity<ApiResponse> updateCompany(
            @Parameter(description = "회사 ID", required = true, example = "1")
            @PathVariable Long companyId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "회사 수정 요청 정보",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CompanyRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "name": "뉴 테크 컴퍼니",
                                                "careerLink": "https://career.newtechcompany.com",
                                                "logoUrl": "https://logo.newtechcompany.com/logo.png",
                                                "description": "더욱 혁신적인 기술 회사입니다."
                                            }
                                            """
                            )
                    )
            )
            @RequestBody CompanyRequest request
    );

    @Operation(
            summary = "회사 삭제",
            description = "특정 회사를 삭제합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "회사 삭제 성공",
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
    ResponseEntity<ApiResponse> deleteCompany(
            @Parameter(description = "회사 ID", required = true, example = "1")
            @PathVariable Long companyId
    );
}
