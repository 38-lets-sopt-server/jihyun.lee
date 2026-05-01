package org.sopt.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.domain.Post;

@Schema(description = "게시글 목록 아이템")
public record PostListItemResponse(
        @Schema(description = "게시글 ID", example = "1") Long id,
        @Schema(description = "제목", example = "안녕하세요") String title,
        @Schema(description = "내용", example = "첫 번째 게시글입니다.") String content,
        @Schema(description = "작성자 닉네임", example = "홍길동") String author
) {
    public PostListItemResponse(Post post) {
        this(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getNickname()
        );
    }
}
