package org.sopt.exception;

import org.springframework.http.HttpStatus;

public enum LikeErrorCode implements ErrorCode {
    LIKE_ALREADY_EXISTS(HttpStatus.CONFLICT, "LIKE_ALREADY_EXISTS", "이미 좋아요를 누른 게시글입니다."),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "LIKE_NOT_FOUND", "좋아요 내역이 존재하지 않습니다."),
    LIKE_CONFLICT(HttpStatus.CONFLICT, "LIKE_CONFLICT", "좋아요 처리 중 충돌이 발생했습니다. 다시 시도해주세요.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    LikeErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override public HttpStatus getHttpStatus() { return httpStatus; }
    @Override public String getCode() { return code; }
    @Override public String getMessage() { return message; }
}
