package org.sopt.exception;

import org.sopt.dto.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Void>> handleCustomException(CustomException e) {
        return toErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<BaseResponse<Void>> handleMissingServletRequestParameter(
            MissingServletRequestParameterException e
    ) {
        return toErrorResponse(CommonErrorCode.MISSING_REQUIRED_PARAM);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<Void>> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException e
    ) {
        return toErrorResponse(CommonErrorCode.INVALID_PARAM_TYPE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Void>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e
    ) {
        return toErrorResponse(CommonErrorCode.INVALID_REQUEST_BODY);
    }

    private ResponseEntity<BaseResponse<Void>> toErrorResponse(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(BaseResponse.error(errorCode.getCode(), errorCode.getMessage()));
    }
}
