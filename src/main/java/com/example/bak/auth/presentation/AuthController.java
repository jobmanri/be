package com.example.bak.auth.presentation;

import com.example.bak.auth.application.AuthService;
import com.example.bak.auth.application.dto.TokenInfo;
import com.example.bak.auth.presentation.dto.LoginRequest;
import com.example.bak.global.common.response.ApiResponse;
import com.example.bak.global.common.response.ApiResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        TokenInfo token = authService.login(request.email(), request.password());
        ApiResponse response = ApiResponseFactory.success("로그인에 성공하였습니다.", token);
        return ResponseEntity.ok(response);
    }
}
