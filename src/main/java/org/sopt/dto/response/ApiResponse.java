package org.sopt.dto.response;

public class ApiResponse<T> {
    private final String code;
    private final String message;
    private final T data;

    private ApiResponse(String code, T data, String message) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(null, data, null);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(null, data, message);
    }

    public static <T> ApiResponse<T> successMessage(String message) {
        return new ApiResponse<>(null, null, message);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(code, null, message);
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}
