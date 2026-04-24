package org.sopt.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.sopt.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
        return toErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingServletRequestParameter(
            MissingServletRequestParameterException e
    ) {
        if ("boardType".equals(e.getParameterName())) {
            return toErrorResponse(ErrorCode.BOARD_TYPE_REQUIRED);
        }
        return toErrorResponse(ErrorCode.INVALID_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException e
    ) {
        if ("boardType".equals(e.getName())) {
            return toErrorResponse(ErrorCode.INVALID_BOARD_TYPE);
        }
        return toErrorResponse(ErrorCode.INVALID_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        if (e.getCause() instanceof InvalidFormatException invalidFormatEx
                && !invalidFormatEx.getPath().isEmpty()) {
            String fieldName = invalidFormatEx.getPath().get(0).getFieldName();
            if ("boardType".equals(fieldName)) {
                return toErrorResponse(ErrorCode.INVALID_BOARD_TYPE);
            }
        }
        return toErrorResponse(ErrorCode.INVALID_REQUEST);
    }

    private ResponseEntity<ApiResponse<Void>> toErrorResponse(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage()));
    }
}
