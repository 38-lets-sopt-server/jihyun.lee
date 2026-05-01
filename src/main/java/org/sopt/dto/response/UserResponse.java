package org.sopt.dto.response;

import org.sopt.domain.User;

public record UserResponse(
        Long id,
        String nickname,
        String email
) {
    public UserResponse(User user) {
        this(user.getId(), user.getNickname(), user.getEmail());
    }
}
