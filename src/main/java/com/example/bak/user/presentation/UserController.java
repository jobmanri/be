package com.example.bak.user.presentation;

import com.example.bak.global.common.response.ApiResponse;
import com.example.bak.global.common.response.ApiResponseFactory;
import com.example.bak.global.common.utils.UriUtils;
import com.example.bak.user.application.UserService;
import com.example.bak.user.application.dto.ProfileResult;
import com.example.bak.user.application.dto.UserResult;
import com.example.bak.user.presentation.dto.UserRequest;
import lombok.RequiredArgsConstructor;
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
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createUser(
            @RequestBody UserRequest request
    ) {
        UserResult userResult = userService.createUser(
                request.email(),
                request.password(),
                request.name(),
                request.nickname()
        );

        ApiResponse response = ApiResponseFactory.successVoid("사용자를 성공적으로 생성하였습니다.");
        return ResponseEntity.created(UriUtils.current(userResult.id(), "profile"))
                .body(response);
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<ApiResponse> getUserProfile(
            @PathVariable Long userId
    ) {
        ProfileResult profileResult = userService.getUserProfile(userId);

        ApiResponse response = ApiResponseFactory.success("프로필 정보를 성공하였습니다.", profileResult);
        return ResponseEntity.ok(response);
    }
}
