package org.sopt.validation;

import org.sopt.domain.BoardType;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.exception.CommonErrorCode;
import org.sopt.exception.CustomException;
import org.sopt.exception.PostErrorCode;

public class PostValidator {
    private static final int MAX_TITLE_LENGTH = 50;

    private PostValidator() {}

    public static void validateCreatePost(CreatePostRequest request) {
        validateBoardType(request.boardType());
        validate(request.title(), request.content());
    }

    public static void validateUpdatePost(UpdatePostRequest request) {
        validate(request.title(), request.content());
    }

    private static void validate(String title, String content) {
        validateTitle(title);
        validateContent(content);
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new CustomException(PostErrorCode.POST_TITLE_REQUIRED);
        }
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new CustomException(PostErrorCode.POST_TITLE_TOO_LONG);
        }
    }

    private static void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new CustomException(PostErrorCode.POST_CONTENT_REQUIRED);
        }
    }

    public static void validatePageParams(int page, int size) {
        if (page < 0) {
            throw new CustomException(CommonErrorCode.INVALID_PAGE_NUMBER);
        }
        if (size < 1 || size > 100) {
            throw new CustomException(CommonErrorCode.INVALID_PAGE_SIZE);
        }
    }

    public static BoardType validateBoardType(String boardType) {
        if (boardType == null || boardType.isBlank()) {
            throw new CustomException(PostErrorCode.BOARD_TYPE_REQUIRED);
        }
        try {
            return BoardType.valueOf(boardType);
        } catch (IllegalArgumentException e) {
            throw new CustomException(PostErrorCode.INVALID_BOARD_TYPE);
        }
    }
}
