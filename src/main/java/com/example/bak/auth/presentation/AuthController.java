package com.example.bak.auth.presentation;

import com.example.bak.auth.application.command.AuthCommandService;
import com.example.bak.auth.presentation.dto.LoginRequest;
import com.example.bak.auth.presentation.dto.SignUpRequest;
import com.example.bak.auth.presentation.swagger.AuthSwagger;
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
public class AuthController implements AuthSwagger {

    private final AuthCommandService authCommandService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        ApiResponse response = ApiResponseFactory.success("로그인에 성공하였습니다.",
                authCommandService.login(request.email(), request.password()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody SignUpRequest request) {
        authCommandService.registerService(request.email(), request.password());
        ApiResponse response = ApiResponseFactory.successVoid("회원가입에 성공하였습니다.");
        return ResponseEntity.ok(response);
    }
}
