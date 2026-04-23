package org.sopt.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "해당 게시글이 존재하지 않습니다."),
    POST_TITLE_REQUIRED(HttpStatus.BAD_REQUEST, "POST_TITLE_REQUIRED", "제목은 필수입니다!"),
    POST_TITLE_TOO_LONG(HttpStatus.BAD_REQUEST, "POST_TITLE_TOO_LONG", "제목은 50글자 이하로 작성해주세요!"),
    POST_CONTENT_REQUIRED(HttpStatus.BAD_REQUEST, "POST_CONTENT_REQUIRED", "내용은 필수입니다!"),

    // Pagination
    INVALID_PAGE_NUMBER(HttpStatus.BAD_REQUEST, "INVALID_PAGE_NUMBER", "페이지 번호는 0 이상이어야 합니다."),
    INVALID_PAGE_SIZE(HttpStatus.BAD_REQUEST, "INVALID_PAGE_SIZE", "페이지 크기는 1 이상 100 이하여야 합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() { return httpStatus; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
}
