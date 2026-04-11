package org.sopt.validation;

import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;

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
            throw new IllegalArgumentException("🚫 제목은 필수입니다!");
        }
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException(("🚫 제목은 50글자 이하로 작성해주세요!"));
        }
    }

    private static void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("🚫 내용은 필수입니다!");
        }
    }
}
