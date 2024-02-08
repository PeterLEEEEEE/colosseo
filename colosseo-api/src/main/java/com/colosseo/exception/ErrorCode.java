package com.colosseo.exception;

import org.springframework.http.HttpStatus;


public enum ErrorCode {
    UNKNOWN_ERROR(HttpStatus.BAD_REQUEST.value(), "COMMON-0001", "알 수 없는 에러가 발생했습니다."),
    NO_PERMISSION(HttpStatus.FORBIDDEN.value(), "COMMON-0002", "접근 권한이 없습니다."),
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "USER-0001", "존재하지 않는 사용자입니다."),
    USER_NOT_EXIST_OR_WRONG_PASSWORD(HttpStatus.UNAUTHORIZED.value(), "USER-0002", "존재하지 않는 사용자이거나 잘못된 비밀번호 입니다."),
    USER_NOT_ENABLED(HttpStatus.BAD_REQUEST.value(), "USER-0003", "잠금 상태의 계정입니다."),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST.value(), "USER-0004", "이미 존재하는 아이디 입니다."),
    PASSWORD_NOT_SAME(HttpStatus.BAD_REQUEST.value(), "USER-0004", "비밀번호가 일치하지 않습니다. 다시 한번 확인해주세요"),

    ARTICLE_NOT_EXISTS(HttpStatus.BAD_REQUEST.value(), "ARTICLE-0001", "존재하지 않는 글입니다."),
    COMMENT_NOT_EXISTS(HttpStatus.BAD_REQUEST.value(), "COMMENT-0001", "존재하지 않는 댓글입니다."),
    COMMENT_LIKE_DUPLICATE(HttpStatus.CONFLICT.value(), "LIKE-0001", "이미 좋아요를 누른 게시글입니다."),
    COMMENT_LIKE_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "LIKE-0002", "없는 좋아요 입니다"),
    VERIFICATION_CODE_NOT_SAME(HttpStatus.BAD_REQUEST.value(), "EMAIL-0001", "인증 코드가 일치하지 않습니다."),
    VERIFICATION_CODE_EXPIRED(HttpStatus.BAD_REQUEST.value(), "EMAIL-0002", "만료된 인증 코드입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED.value(), "TOKEN-0001", "보관된 리프레시 토큰이 존재하지 않습니다"),
    JWT_ACCESS_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED.value(), "TOKEN-0002", "토큰이 존재하지 않습니다."),
    JWT_WRONG_SIGNATURE(HttpStatus.UNAUTHORIZED.value(), "TOKEN-0003", "유효한 시그니처가 아닙니다."),
    JWT_UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "TOKEN-0004", "지원하지 않는 토큰입니다."),
    JWT_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "TOKEN-0005", "만료된 토큰입니다."),
    JWT_INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "TOKEN-0006", "잘못된 형식의 토큰입니다."),
    JWT_COMMON_ERROR(HttpStatus.UNAUTHORIZED.value(), "TOKEN-0007", "잘못된 토큰입니다."),

    COOKIE_DESERIALIZATION_ERROR(HttpStatus.BAD_REQUEST.value(), "COOKIE-0001", "잘못된 쿠키 형식입니다."),

    PROVIDER_ALREADY_EXIST(HttpStatus.BAD_REQUEST.value(), "OAUTH-0001", "이미 가입된 소셜 로그인 계정이 있습니다.");
    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
