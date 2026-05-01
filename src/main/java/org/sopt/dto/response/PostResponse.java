package org.sopt.dto.response;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;

public record PostResponse (
        Long id,
        String title,
        String content,
        String author,
        String createdAt,
        BoardType boardType
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
