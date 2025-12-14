package com.example.bak.feed.presentation;

import com.example.bak.feed.application.command.FeedCommandService;
import com.example.bak.feed.application.command.dto.FeedResult;
import com.example.bak.feed.application.query.FeedQueryService;
import com.example.bak.feed.application.query.dto.FeedDetail;
import com.example.bak.feed.application.query.dto.FeedSummary;
import com.example.bak.feed.presentation.dto.FeedRequest;
import com.example.bak.feed.presentation.dto.FeedUpdateRequest;
import com.example.bak.feed.presentation.swagger.FeedSwagger;
import com.example.bak.global.common.response.ApiResponse;
import com.example.bak.global.common.response.ApiResponseFactory;
import com.example.bak.global.common.utils.UriUtils;
import com.example.bak.global.security.annotation.AuthUser;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feeds")
@RequiredArgsConstructor
public class FeedController implements FeedSwagger {

    private final FeedCommandService feedCommandService;
    private final FeedQueryService feedQueryService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createFeed(@AuthUser Long userId,
            @RequestBody FeedRequest request) {
        FeedResult feedResult = feedCommandService.createFeed(
                request.title(),
                request.content(),
                request.communityId(),
                userId
        );
        ApiResponse response = ApiResponseFactory.successVoid("피드를 성공적으로 생성하였습니다.");
        return ResponseEntity.created(UriUtils.current(feedResult.id()))
                .body(response);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getFeeds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<FeedSummary> feeds = feedQueryService.getFeeds(page, size);
        ApiResponse response = ApiResponseFactory.success("피드 목록을 성공적으로 조회하였습니다.", feeds);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{feedId}/summary")
    public ResponseEntity<ApiResponse> getFeedSummary(@PathVariable Long feedId) {
        FeedSummary summary = feedQueryService.getFeedSummary(feedId);
        ApiResponse response = ApiResponseFactory.success("피드 요약을 성공적으로 조회하였습니다.", summary);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{feedId}")
    public ResponseEntity<ApiResponse> getFeedDetail(@PathVariable Long feedId) {
        FeedDetail detail = feedQueryService.getFeedDetail(feedId);
        ApiResponse response = ApiResponseFactory.success("피드 상세를 성공적으로 조회하였습니다.", detail);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{feedId}")
    public ResponseEntity<ApiResponse> updateFeed(
            @AuthUser Long userId,
            @PathVariable Long feedId,
            @RequestBody FeedUpdateRequest request
    ) {
        feedCommandService.updateFeed(feedId, request.title(), request.content(), userId);
        ApiResponse response = ApiResponseFactory.successVoid("피드를 성공적으로 수정하였습니다.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{feedId}")
    public ResponseEntity<ApiResponse> deleteFeed(
            @AuthUser Long userId,
            @PathVariable Long feedId
    ) {
        feedCommandService.deleteFeed(feedId, userId);
        ApiResponse response = ApiResponseFactory.successVoid("피드를 성공적으로 삭제하였습니다.");
        return ResponseEntity.ok(response);
    }
}
