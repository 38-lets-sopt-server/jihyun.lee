package org.sopt.service;

import org.sopt.domain.Like;
import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.response.IdResponse;
import org.sopt.exception.CustomException;
import org.sopt.exception.LikeErrorCode;
import org.sopt.exception.PostErrorCode;
import org.sopt.exception.UserErrorCode;
import org.sopt.repository.LikeRepository;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public LikeService(
            LikeRepository likeRepository,
            PostRepository postRepository,
            UserRepository userRepository
    ) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public IdResponse addLike(Long postId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));

        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new CustomException(LikeErrorCode.LIKE_ALREADY_EXISTS);
        }

        Like like = likeRepository.save(new Like(user, post));
        return new IdResponse(like.getId());
    }

    @Transactional
    public void cancelLike(Long postId, Long userId) {
        Like like = likeRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new CustomException(LikeErrorCode.LIKE_NOT_FOUND));
        likeRepository.delete(like);
    }
}
