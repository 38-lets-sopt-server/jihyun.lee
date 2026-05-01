package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 생성 요청")
public record CreateUserRequest(
        @Schema(description = "닉네임", example = "홍길동") String nickname,
        @Schema(description = "이메일", example = "hong@example.com") String email
) {
}
