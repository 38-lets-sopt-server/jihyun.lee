package org.sopt.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "게시글 검색 응답")
public record PostSearchResponse(
        @Schema(description = "검색된 게시글 목록") List<PostListItemResponse> posts,
        @Schema(description = "검색 키워드", example = "안녕") String keyword,
        @Schema(description = "현재 페이지 번호", example = "0") int page,
        @Schema(description = "페이지 크기", example = "10") int size,
        @Schema(description = "다음 페이지 존재 여부", example = "false") boolean hasNext
) {
}
