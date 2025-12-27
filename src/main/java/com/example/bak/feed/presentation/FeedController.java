package com.example.bak.feed.presentation;

import com.example.bak.feed.application.command.FeedCommandService;
import com.example.bak.feed.application.command.dto.FeedResult;
import com.example.bak.feed.application.query.FeedQueryService;
import com.example.bak.feed.application.query.dto.FeedDetail;
import com.example.bak.feed.application.query.dto.FeedSummary;
import com.example.bak.feed.application.query.dto.FeedTitleOnly;
import com.example.bak.feed.presentation.dto.CommunityFeedsResponse;
import com.example.bak.feed.presentation.dto.FeedRequest;
import com.example.bak.feed.presentation.dto.FeedUpdateRequest;
import com.example.bak.feed.presentation.dto.MainPageFeedsResponse;
import com.example.bak.feed.presentation.dto.MainPageTitlesResponse;
import com.example.bak.feed.presentation.swagger.FeedSwagger;
import com.example.bak.global.common.response.ApiResponse;
import com.example.bak.global.common.response.ApiResponseFactory;
import com.example.bak.global.common.utils.UriUtils;
import com.example.bak.global.security.annotation.AuthUser;
import com.example.bak.like.application.command.LikeCommandService;
import com.example.bak.like.domain.LikeTargetType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FeedController implements FeedSwagger {

    private final FeedCommandService feedCommandService;
    private final LikeCommandService likeCommandService;
    private final FeedQueryService feedQueryService;

    @PostMapping("/feeds")
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

    @GetMapping("/feeds")
    public ResponseEntity<ApiResponse> getFeeds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<FeedSummary> feeds = feedQueryService.getFeeds(page, size);
        ApiResponse response = ApiResponseFactory.success("피드 목록을 성공적으로 조회하였습니다.", feeds);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/feeds/main")
    public ResponseEntity<ApiResponse> getMainPageFeeds(
            @RequestParam(defaultValue = "10") int limit
    ) {
        List<FeedDetail> feeds = feedQueryService.getMainPageFeeds(limit);
        ApiResponse response = ApiResponseFactory.success(
                "메인 페이지 피드를 성공적으로 조회하였습니다.",
                new MainPageFeedsResponse(feeds)
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/feeds/main/titles")
    public ResponseEntity<ApiResponse> getMainPageTitles(
            @RequestParam(defaultValue = "5") int limit
    ) {
        ApiResponse response = ApiResponseFactory.success(
                "메인 페이지 커뮤니티별 제목을 성공적으로 조회하였습니다.",
                new MainPageTitlesResponse(feedQueryService.getCommunityTitleSectionsForMainPage(limit))
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/communities/{communityId}/feeds")
    public ResponseEntity<ApiResponse> getFeedsByCommunity(
            @PathVariable Long communityId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size
    ) {
        FeedQueryService.CommunityPageResult result = feedQueryService.getFeedsByCommunity(communityId, cursor,
                size);
        CommunityFeedsResponse responseBody = new CommunityFeedsResponse(
                result.feeds(),
                result.nextCursor()
        );
        ApiResponse response = ApiResponseFactory.success("커뮤니티 피드를 성공적으로 조회하였습니다.",
                responseBody);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/communities/{communityId}/feeds/titles")
    public ResponseEntity<ApiResponse> getFeedTitlesByCommunity(
            @PathVariable Long communityId,
            @RequestParam(defaultValue = "5") int limit
    ) {
        List<FeedTitleOnly> titles = feedQueryService.getFeedTitlesByCommunity(communityId, limit);
        ApiResponse response = ApiResponseFactory.success("커뮤니티 피드 제목을 성공적으로 조회하였습니다.", titles);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/feeds/{feedId}/summary")
    public ResponseEntity<ApiResponse> getFeedSummary(@PathVariable Long feedId) {
        FeedSummary summary = feedQueryService.getFeedSummary(feedId);
        ApiResponse response = ApiResponseFactory.success("피드 요약을 성공적으로 조회하였습니다.", summary);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/feeds/{feedId}")
    public ResponseEntity<ApiResponse> getFeedDetail(@PathVariable Long feedId) {
        FeedDetail detail = feedQueryService.getFeedDetail(feedId);
        ApiResponse response = ApiResponseFactory.success("피드 상세를 성공적으로 조회하였습니다.", detail);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/feeds/{feedId}")
    public ResponseEntity<ApiResponse> updateFeed(
            @AuthUser Long userId,
            @PathVariable Long feedId,
            @RequestBody FeedUpdateRequest request
    ) {
        feedCommandService.updateFeed(feedId, request.title(), request.content(), userId);
        ApiResponse response = ApiResponseFactory.successVoid("피드를 성공적으로 수정하였습니다.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/feeds/{feedId}")
    public ResponseEntity<ApiResponse> deleteFeed(
            @AuthUser Long userId,
            @PathVariable Long feedId
    ) {
        feedCommandService.deleteFeed(feedId, userId);
        ApiResponse response = ApiResponseFactory.successVoid("피드를 성공적으로 삭제하였습니다.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/feeds/{feedId}/likes")
    public ResponseEntity<ApiResponse> likeFeed(
            @AuthUser Long userId,
            @PathVariable Long feedId
    ) {
        likeCommandService.like(LikeTargetType.FEED, feedId, userId);
        ApiResponse response = ApiResponseFactory.successVoid("피드에 좋아요를 추가했습니다.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/feeds/{feedId}/likes")
    public ResponseEntity<ApiResponse> unlikeFeed(
            @AuthUser Long userId,
            @PathVariable Long feedId
    ) {
        likeCommandService.unlike(LikeTargetType.FEED, feedId, userId);
        ApiResponse response = ApiResponseFactory.successVoid("피드 좋아요를 취소했습니다.");
        return ResponseEntity.ok(response);
    }
}
