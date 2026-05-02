package org.sopt.exception;

public class LikeAlreadyExistsException extends CustomException {
    public LikeAlreadyExistsException() {
        super(ErrorCode.LIKE_ALREADY_EXISTS);
    }
}
