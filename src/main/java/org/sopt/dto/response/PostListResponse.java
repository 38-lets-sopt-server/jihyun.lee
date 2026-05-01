package org.sopt.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.domain.BoardType;

import java.util.List;

@Schema(description = "게시글 목록 응답")
public record PostListResponse(
        @Schema(description = "게시글 목록") List<PostListItemResponse> posts,
        @Schema(description = "게시판 종류", example = "FREE") BoardType boardType,
        @Schema(description = "현재 페이지 번호", example = "0") int page,
        @Schema(description = "페이지 크기", example = "10") int size,
        @Schema(description = "다음 페이지 존재 여부", example = "false") boolean hasNext
) {
}
