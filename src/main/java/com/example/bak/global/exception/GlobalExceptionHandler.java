package com.example.bak.global.exception;

import com.example.bak.global.common.response.ApiResponse;
import com.example.bak.global.common.response.ApiResponseFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse> handleBusinessException(BusinessException exception) {
        ErrorCode code = exception.getErrorCode();
        ApiResponse response = ApiResponseFactory.failure(code.getMessage());
        return ResponseEntity.status(code.getStatus()).body(response);
    }
}
