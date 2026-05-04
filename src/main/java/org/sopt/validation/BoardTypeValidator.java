package org.sopt.validation;

import org.sopt.domain.BoardType;
import org.sopt.exception.CustomException;
import org.sopt.exception.PostErrorCode;

public class BoardTypeValidator {

    private BoardTypeValidator() {}

    public static BoardType parse(String value) {
        if (value == null || value.isBlank()) {
            throw new CustomException(PostErrorCode.BOARD_TYPE_REQUIRED);
        }
        try {
            return BoardType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new CustomException(PostErrorCode.INVALID_BOARD_TYPE);
        }
    }
}
