package org.sopt.exception;

import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "UNAUTHENTICATED", "인증이 필요합니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", "이메일 또는 비밀번호가 올바르지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "INVALID_REFRESH_TOKEN", "Refresh Token이 유효하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    AuthErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override public HttpStatus getHttpStatus() { return httpStatus; }
    @Override public String getCode() { return code; }
    @Override public String getMessage() { return message; }
}
