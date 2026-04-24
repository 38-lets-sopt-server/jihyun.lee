package org.sopt.dto.response;

import org.sopt.domain.BoardType;

import java.util.List;

public record PostListResponse (
        List<PostListItemResponse> posts,
        BoardType boardType,
        int page,
        int size,
        boolean hasNext
) {
}
