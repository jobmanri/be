package com.example.bak.global.common;

import lombok.Getter;

@Getter
public class FailureApiResponse extends ApiResponse{

    protected FailureApiResponse(String message) {
        super(false, message);
    }
}
