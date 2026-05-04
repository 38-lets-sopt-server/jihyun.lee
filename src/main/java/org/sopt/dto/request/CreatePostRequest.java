package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 생성 요청")
public record CreatePostRequest(
        @Schema(description = "게시글 제목 (최대 50자)", example = "안녕하세요") String title,
        @Schema(description = "게시글 내용", example = "첫 번째 게시글입니다.") String content,
        @Schema(description = "작성자 사용자 ID", example = "1") Long userId,
        @Schema(description = "게시판 종류 (FREE, HOT, SECRET)", example = "FREE") String boardType
) {
}
