package com.example.bak.community.presentation;

import com.example.bak.community.application.CommunityService;
import com.example.bak.community.application.dto.CommunityResult;
import com.example.bak.community.presentation.dto.CommunityRequest;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/companies/{companyId}/communities")
    public ResponseEntity<ApiResponse> getCommunities(
            @PathVariable Long companyId
    ) {
        List<CommunityResult.Detail> communityResults = communityService.getCommunities(companyId);
        ApiResponse response = ApiResponseFactory.success("커뮤니티 목록을 성공적으로 불러왔습니다.",
                communityResults);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/companies/{companyId}/communities")
    public ResponseEntity<ApiResponse> createCommunity(
            @PathVariable Long companyId,
            @RequestBody CommunityRequest request
    ) {
        CommunityResult.ResourcePath resourcePath = communityService.createCommunity(
                companyId, request.name(), request.jobGroup());
        ApiResponse response = ApiResponseFactory.successVoid("커뮤니티 정보를 성공적으로 저장했습니다.");
        return ResponseEntity.created(UriUtils.current(resourcePath.communityId()))
                .body(response);
    }

    @PutMapping("/communities/{communityId}")
    public ResponseEntity<ApiResponse> updateCompanyCommunity(
            @PathVariable Long communityId,
            @RequestBody CommunityRequest request
    ) {
        communityService.updateCommunity(communityId, request.name(), request.jobGroup());
        ApiResponse response = ApiResponseFactory.successVoid("커뮤니티 정보를 성공적으로 수정했습니다.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/communities/{communityId}")
    public ResponseEntity<ApiResponse> deleteCommunityCompany(
            @PathVariable Long communityId
    ) {
        communityService.deleteCommunity(communityId);
        ApiResponse response = ApiResponseFactory.successVoid("회사 정보를 성공적으로 삭제했습니다.");
        return ResponseEntity.ok(response);
    }
}
