package org.sopt.validation;

import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.exception.CustomException;
import org.sopt.exception.ErrorCode;

public class PostValidator {
    private static final int MAX_TITLE_LENGTH = 50;

    private PostValidator() {}

    public static void validateCreatePost(CreatePostRequest request) {
        validate(request.title, request.content);
    }

    public static void validateUpdatePost(UpdatePostRequest request) {
        validate(request.title, request.content);
    }

    private static void validate(String title, String content) {
        validateTitle(title);
        validateContent(content);
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new CustomException(ErrorCode.POST_TITLE_REQUIRED);
        }
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new CustomException(ErrorCode.POST_TITLE_TOO_LONG);
        }
    }

    private static void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new CustomException(ErrorCode.POST_CONTENT_REQUIRED);
        }
    }
}
