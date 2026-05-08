package org.sopt.dto.response;

public record BaseResponse<T> (
        String code,
        String message,
        T data
) {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(null, null, data);
    }

    public static <T> BaseResponse<T> error(String code, String message) {
        return new BaseResponse<>(code, message, null);
    }
}
