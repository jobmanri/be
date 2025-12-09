package com.example.bak.user.presentation;

import com.example.bak.global.common.response.ApiResponse;
import com.example.bak.global.common.response.ApiResponseFactory;
import com.example.bak.global.common.utils.UriUtils;
import com.example.bak.global.security.annotation.AuthUser;
import com.example.bak.user.application.command.UserCommandService;
import com.example.bak.user.application.command.dto.UserCommandResult;
import com.example.bak.user.application.query.UserQueryService;
import com.example.bak.user.application.query.dto.ProfileResult;
import com.example.bak.user.presentation.dto.ProfileRequest;
import com.example.bak.user.presentation.dto.UserRequest;
import com.example.bak.user.presentation.swagger.UserSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController implements UserSwagger {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    
    @PostMapping()
    public ResponseEntity<ApiResponse> createUser(
            @RequestBody UserRequest request
    ) {
        UserCommandResult userResult = userCommandService.createUser(
                request.email(),
                request.password()
        );

        ApiResponse response = ApiResponseFactory.successVoid("사용자를 성공적으로 생성하였습니다.");
        return ResponseEntity.created(UriUtils.current(userResult.id(), "profile"))
                .body(response);
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<ApiResponse> getUserProfile(
            @PathVariable Long userId
    ) {
        ProfileResult profileResult = userQueryService.getUserProfile(userId);

        ApiResponse response = ApiResponseFactory.success("프로필 정보를 성공하였습니다.", profileResult);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile")
    public ResponseEntity<ApiResponse> createProfile(@AuthUser Long userId,
            @RequestBody ProfileRequest request) {
        ApiResponse response = ApiResponseFactory.success("프로필을 생성하였습니다.",
                userCommandService.createProfile(userId, request.name(), request.nickname()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
