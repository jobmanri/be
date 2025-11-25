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
    // Auth
    INCORRECT_PASSWORD("AU001", HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다"),
    INVALID_TOKEN("AU002", HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다"),
    NO_ATTRIBUTES("AU003", HttpStatus.BAD_REQUEST, "토큰 검증에 필요한 정보가 없습니다."),
    NO_TOKEN("AU004", HttpStatus.UNAUTHORIZED, "토큰이 없습니다."),
    NO_AUTHORITY("AU005", HttpStatus.FORBIDDEN, "해당 정보에 접근할 수 있는 권한이 없습니다."),
    // Company
    COMPANY_NOT_FOUND("CM001", HttpStatus.NOT_FOUND, "회사 리소스를 찾을 수 없습니다."),
    COMPANY_COMMUNITY_NOT_FOUND("CC001", HttpStatus.NOT_FOUND, "커뮤니티 리소스를 찾을 수 없습니다."),

    // User
    USER_NOT_FOUND("US001", HttpStatus.NOT_FOUND, "사용자 리소스를 찾을 수 없습니다."),
    CANNOT_CONVERT_ROLE("US002", HttpStatus.BAD_REQUEST, "올바르지 않은 Parameter 입력입니다."),

    // Feed
    FEED_NOT_FOUND("FE001", HttpStatus.NOT_FOUND, "피드 리소스를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND("CM001", HttpStatus.NOT_FOUND, "댓글 리소스를 찾을 수 없습니다."),

    // Community
    COMMUNITY_NOT_FOUND("CO001", HttpStatus.NOT_FOUND, "커뮤니티 리소스를 찾을 수 없습니다."),

    // Private Message
    MESSAGE_NOT_FOUND("PM001", HttpStatus.NOT_FOUND, "쪽지 리소스를 찾을 수 없습니다."),

    // Global
    UNAUTHORIZED_ACTION("CM002", HttpStatus.FORBIDDEN, "권한이 없는 작업입니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;
}
