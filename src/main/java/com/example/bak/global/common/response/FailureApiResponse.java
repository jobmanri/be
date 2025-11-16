package com.example.bak.global.common.response;

import lombok.Getter;

@Getter
public class FailureApiResponse extends ApiResponse {

    protected FailureApiResponse(String message) {
        super(false, message);
    }
}
