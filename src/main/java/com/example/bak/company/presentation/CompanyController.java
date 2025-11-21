package com.example.bak.company.presentation;

import com.example.bak.company.application.CompanyService;
import com.example.bak.company.application.dto.CompanyResult;
import com.example.bak.company.application.dto.CompanyResult.CompanyId;
import com.example.bak.company.presentation.dto.CompanyRequest;
import com.example.bak.global.common.response.ApiResponse;
import com.example.bak.global.common.response.ApiResponseFactory;
import com.example.bak.global.common.utils.UriUtils;
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
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping()
    public ResponseEntity<ApiResponse> getCompanies() {
        List<CompanyResult.Detail> companyResults = companyService.getCompanies();
        ApiResponse response = ApiResponseFactory.success(
                "회사 목록을 성공적으로 조회했습니다.", companyResults);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<ApiResponse> getCompany(
            @PathVariable Long companyId
    ) {
        CompanyResult.Detail companyResult = companyService.getCompany(companyId);
        ApiResponse response = ApiResponseFactory.success("회사 정보를 성공적으로 조회했습니다.", companyResult);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createCompany(
            @RequestBody CompanyRequest request
    ) {
        CompanyId companyId = companyService.createCompany(
                request.name(), request.careerLink(), request.logoUrl(), request.description()
        );
        ApiResponse response = ApiResponseFactory.successVoid("회사 정보를 성공적으로 생성했습니다.");
        return ResponseEntity.created(UriUtils.current(companyId.value()))
                .body(response);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<ApiResponse> updateCompany(
            @PathVariable Long companyId,
            @RequestBody CompanyRequest request
    ) {
        companyService.updateCompany(
                companyId, request.name(), request.careerLink(), request.logoUrl(),
                request.description()
        );
        ApiResponse response = ApiResponseFactory.successVoid("회사 정보를 성공적으로 수정했습니다.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<ApiResponse> deleteCompany(
            @PathVariable Long companyId
    ) {
        companyService.deleteCompany(companyId);
        ApiResponse response = ApiResponseFactory.successVoid("회사 정보를 성공적으로 삭제했습니다.");
        return ResponseEntity.ok(response);
    }
}
