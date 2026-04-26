package org.sopt.dto.request;

public record CreatePostRequest (
        String title,
        String content,
        Long userId,
        String boardType
) {
}
