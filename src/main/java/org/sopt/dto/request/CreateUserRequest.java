package org.sopt.dto.request;

public record CreateUserRequest(
        String nickname,
        String email
) {
}
