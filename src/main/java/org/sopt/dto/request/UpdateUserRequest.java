package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 수정 요청")
public record UpdateUserRequest(
        @Schema(description = "수정할 닉네임", example = "김철수") String nickname
) {
}
