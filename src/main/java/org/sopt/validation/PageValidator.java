package org.sopt.validation;

import org.sopt.exception.CommonErrorCode;
import org.sopt.exception.CustomException;

public class PageValidator {

    private PageValidator() {}

    public static void validate(int page, int size) {
        if (page < 0) {
            throw new CustomException(CommonErrorCode.INVALID_PAGE_NUMBER);
        }
        if (size < 1 || size > 100) {
            throw new CustomException(CommonErrorCode.INVALID_PAGE_SIZE);
        }
    }
}
