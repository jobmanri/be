package com.example.bak.company.presentation;

import com.example.bak.company.application.CompanyService;
import com.example.bak.company.application.dto.CompanyResult;
import com.example.bak.company.presentation.dto.CompanyRequest;
import com.example.bak.global.common.ApiResponse;
import com.example.bak.global.common.ApiResponseFactory;
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

    // 회사 전체 조회
    @GetMapping()
    public ResponseEntity<ApiResponse> getCompanies() {
        List<CompanyResult> companyResults = companyService.getCompanies();
        ApiResponse response = ApiResponseFactory.success(
                "회사 정보를 성공적으로 조회했습니다!", companyResults);
        return null;
    }

    // 회사 단일 조회
    @GetMapping("/{companyId}")
    public ResponseEntity<ApiResponse> getCompany(
            @PathVariable Long companyId
    ) {
        CompanyResult companyResult = companyService.getCompany(companyId);
        return null;
    }

    // 회사 등록
    @PostMapping()
    public ResponseEntity<ApiResponse> createCompany(
            @RequestBody CompanyRequest request
    ) {
        companyService.createCompany(
                request.name(), request.careerLink(), request.logoUrl(), request.description()
        );
        return null;
    }

    // 회사 정보 수정
    @PutMapping("/{companyId}")
    public ResponseEntity<ApiResponse> updateCompany(
            @PathVariable Long companyId,
            @RequestBody CompanyRequest request
    ) {
        companyService.updateCompany(
                companyId, request.name(), request.careerLink(), request.logoUrl(),
                request.description()
        );
        return null;
    }

    // 회사 삭제
    @DeleteMapping("/{companyId}")
    public ResponseEntity<ApiResponse> deleteCompany(
            @PathVariable Long companyId
    ) {

        return null;
    }
}
