package com.example.bak.global.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiResponseFactory {

    public static <T> ApiResponse success(String message, T data) {
        return new SuccessApiResponse<>(message, data);
    }

    public static ApiResponse successVoid(String message) {
        return new SuccessApiResponse<>(message);
    }

    public static ApiResponse failure(String message) {
        return new FailureApiResponse(message);
    }
}
