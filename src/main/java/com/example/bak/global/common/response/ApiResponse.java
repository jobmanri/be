package com.example.bak.global.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class ApiResponse {

    private final boolean isSuccess;
    private final String message;
}