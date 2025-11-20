package com.example.bak.community.presentation;

import com.example.bak.community.application.CommunityService;
import com.example.bak.community.application.dto.CommunityResult;
import com.example.bak.community.presentation.dto.CommunityRequest;
import com.example.bak.global.common.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    // 회사 별 커뮤니티 조회
    @GetMapping("/{companyId}/communities")
    public ResponseEntity<ApiResponse> getCommunities(
            @PathVariable Long companyId
    ) {
        List<CommunityResult> communityResults = communityService.getCompanyCommunities(companyId);
        return null;
    }

    // 회사별 커뮤니티 등록
    @PostMapping("/{companyId}/communities")
    public ResponseEntity<ApiResponse> createCommunity(
            @PathVariable Long companyId,
            @RequestBody CommunityRequest request
    ) {
        communityService.createCompanyCommunity(companyId, request.name(), request.jobGroup());
        return null;
    }

    // 회사 커뮤니티 정보 수정
    @PutMapping("/{companyId}/communities/{communityId}")
    public ResponseEntity<ApiResponse> updateCompanyCommunity(
            @PathVariable Long companyId,
            @PathVariable Long communityId,
            @RequestBody CommunityRequest request
    ) {
        communityService.updateCompanyCommunity(
                companyId, communityId, request.name(), request.jobGroup()
        );
        return null;
    }

    // 회사 커뮤니티 삭제
    @DeleteMapping("/{companyId}/communities/{communityId}")
    public ResponseEntity<ApiResponse> deleteCommunityCompany(
            @PathVariable Long companyId,
            @PathVariable Long communityId
    ) {
        communityService.deleteCompanyCommunity(companyId, communityId);
        return null;
    }
}
