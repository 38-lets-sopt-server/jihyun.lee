package org.sopt.validation;

import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;

public class PostValidator {
    private PostValidator() {}

    public static void validateCreatePost(CreatePostRequest request) {
        validateTitle(request.title);
        validateContent(request.content);
    }

    public static void validateUpdatePost(UpdatePostRequest request) {
        validateTitle(request.title);
        validateContent(request.content);
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("🚫 제목은 필수입니다!");
        } else if (title.length() > 50) {
            throw new IllegalArgumentException(("🚫 제목은 50글자 이하로 작성해주세요!"));
        }
    }

    private static void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("🚫 내용은 필수입니다!");
        }
    }
}
