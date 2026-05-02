package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "좋아요 요청")
public record LikeRequest(
        @Schema(description = "사용자 ID", example = "1") Long userId
) {
}
