package org.sopt.dto.request;

import org.sopt.domain.BoardType;

public record CreatePostRequest (
        String title,
        String content,
        String author,
        BoardType boardType
) {
}
