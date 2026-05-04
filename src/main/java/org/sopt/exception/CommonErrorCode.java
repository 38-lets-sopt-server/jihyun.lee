package org.sopt.exception;

import org.springframework.http.HttpStatus;

public enum CommonErrorCode implements ErrorCode {
    MISSING_REQUIRED_PARAM(HttpStatus.BAD_REQUEST, "MISSING_REQUIRED_PARAM", "필수 파라미터가 누락되었습니다."),
    INVALID_PARAM_TYPE(HttpStatus.BAD_REQUEST, "INVALID_PARAM_TYPE", "파라미터 타입이 올바르지 않습니다."),
    INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "INVALID_REQUEST_BODY", "요청 본문을 읽을 수 없습니다."),
    INVALID_PAGE_NUMBER(HttpStatus.BAD_REQUEST, "INVALID_PAGE_NUMBER", "페이지 번호는 0 이상이어야 합니다."),
    INVALID_PAGE_SIZE(HttpStatus.BAD_REQUEST, "INVALID_PAGE_SIZE", "페이지 크기는 1 이상 100 이하여야 합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    CommonErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override public HttpStatus getHttpStatus() { return httpStatus; }
    @Override public String getCode() { return code; }
    @Override public String getMessage() { return message; }
}
