package org.sopt.validation;

import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.exception.CustomException;
import org.sopt.exception.PostErrorCode;

public class PostValidator {
    private static final int MAX_TITLE_LENGTH = 50;

    private PostValidator() {}

    public static void validateCreatePost(CreatePostRequest request) {
        BoardTypeValidator.parse(request.boardType());
        validateTitle(request.title());
        validateContent(request.content());
    }

    public static void validateUpdatePost(UpdatePostRequest request) {
        validateTitle(request.title());
        validateContent(request.content());
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
}
