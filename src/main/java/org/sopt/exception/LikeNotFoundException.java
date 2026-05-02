package org.sopt.exception;

public class LikeNotFoundException extends CustomException {
    public LikeNotFoundException() {
        super(ErrorCode.LIKE_NOT_FOUND);
    }
}
