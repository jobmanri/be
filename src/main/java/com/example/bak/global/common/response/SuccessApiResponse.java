package com.example.bak.global.common.response;

import lombok.Getter;

@Getter
public class SuccessApiResponse<T> extends ApiResponse {

    private final T data;

    protected SuccessApiResponse(String message, T data) {
        super(true, message);
        this.data = data;
    }

    public SuccessApiResponse(String message) {
        super(true, message);
        this.data = null;
    }
}
