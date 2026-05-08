package org.sopt.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.domain.BoardType;
import org.sopt.domain.Post;

@Schema(description = "게시글 단건 응답")
public record PostResponse(
        @Schema(description = "게시글 ID", example = "1") Long id,
        @Schema(description = "제목", example = "안녕하세요") String title,
        @Schema(description = "내용", example = "첫 번째 게시글입니다.") String content,
        @Schema(description = "작성자 닉네임", example = "홍길동") String author,
        @Schema(description = "작성일시", example = "2024-05-01T12:00:00") String createdAt,
        @Schema(description = "게시판 종류", example = "FREE") BoardType boardType
) {
    public PostResponse(Post post) {
        this(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getNickname(),
                post.getCreatedAt().toString(),
                post.getBoardType()
        );
    }
}
