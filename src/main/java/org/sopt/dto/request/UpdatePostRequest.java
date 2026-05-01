package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 수정 요청")
public record UpdatePostRequest(
        @Schema(description = "수정할 제목 (최대 50자)", example = "수정된 제목입니다.") String title,
        @Schema(description = "수정할 내용", example = "수정된 내용입니다.") String content
) {
}
