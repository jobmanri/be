package com.example.bak.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * code - 도메인 별 코드 <br>
 * -> (ex - 'US001' [도메인의 N번째 에러에 해당])<br>
 * status - Http 상태 코드<br>
 * message - 클라이언트에게 응답해주는 메시지
 * @author polyglot-k
 * @version 0.0.1
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    ;

    private final String code;
    private final HttpStatus status;
    private final String message;
}
