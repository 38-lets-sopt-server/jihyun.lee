package org.sopt.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "ID 응답")
public record IdResponse(
        @Schema(description = "생성/수정된 리소스 ID", example = "1") Long id
) {
}
