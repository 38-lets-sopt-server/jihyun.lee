package org.sopt.dto.request;

public record CreatePostRequest (
        String title,
        String content,
        String author,
        String boardType
) {
}
