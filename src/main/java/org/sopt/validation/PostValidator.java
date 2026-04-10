package org.sopt.validation;

import org.sopt.dto.request.CreatePostRequest;

public class PostValidator {
    private PostValidator() {}

    public static void validateCreatePost(CreatePostRequest request) {
        validateTitle(request.title);
        validateContent(request.content);
    }

    public static void validateUpdatePost(String newTitle, String newContent) {
        validateTitle(newTitle);
        validateContent(newContent);
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다!");
        }
    }

    private static void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("내용은 필수입니다!");
        }
    }
}
