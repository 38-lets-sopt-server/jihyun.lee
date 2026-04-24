package org.sopt.dto.response;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;

public record PostListItemResponse(
        Long id,
        String title,
        String content,
        String author,
        BoardType boardType
) {
    public PostListItemResponse(Post post) {
        this(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getBoardType()
        );
    }
}
