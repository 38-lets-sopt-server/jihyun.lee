package org.sopt.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.domain.User;

@Schema(description = "사용자 응답")
public record UserResponse(
        @Schema(description = "사용자 ID", example = "1") Long id,
        @Schema(description = "닉네임", example = "홍길동") String nickname,
        @Schema(description = "이메일", example = "hong@example.com") String email
) {
    public UserResponse(User user) {
        this(user.getId(), user.getNickname(), user.getEmail());
    }
}
