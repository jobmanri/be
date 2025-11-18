package com.example.bak.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * code - 도메인 별 코드 <br> -> (ex - 'US001' [도메인의 N번째 에러에 해당])<br> status - Http 상태 코드<br> message -
 * 클라이언트에게 응답해주는 메시지
 *
 * @author polyglot-k
 * @version 0.0.1
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // User
    USER_NOT_FOUND("US001", HttpStatus.NOT_FOUND, "사용자 리소스를 찾을 수 없습니다."),

    // Feed
    FEED_NOT_FOUND("FE001", HttpStatus.NOT_FOUND, "피드 리소스를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND("CM001", HttpStatus.NOT_FOUND, "댓글 리소스를 찾을 수 없습니다."),
    UNAUTHORIZED_ACTION("CM002", HttpStatus.FORBIDDEN, "권한이 없는 작업입니다."),

    // Community
    COMMUNITY_NOT_FOUND("CO001", HttpStatus.NOT_FOUND, "커뮤니티 리소스를 찾을 수 없습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;
}
