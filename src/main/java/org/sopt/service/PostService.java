package org.sopt.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.BoardType;
import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.*;
import org.sopt.exception.CustomException;
import org.sopt.exception.PostErrorCode;
import org.sopt.exception.UserErrorCode;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.sopt.validation.BoardTypeValidator;
import org.sopt.validation.PageValidator;
import org.sopt.validation.PostValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public IdResponse createPost(CreatePostRequest request, Long userId) {
        PostValidator.validateCreatePost(request);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        Post post = postRepository.save(new Post(
                request.title(),
                request.content(),
                user,
                BoardType.valueOf(request.boardType())
        ));
        return new IdResponse(post.getId());
    }

    @Transactional(readOnly = true)
    public PostListResponse getAllPosts(String boardType, int page, int size) {
        PageValidator.validate(page, size);
        BoardType type = BoardTypeValidator.parse(boardType);

        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.findByBoardTypeWithUser(type, pageable);
        List<PostListItemResponse> posts = postPage.getContent()
                .stream()
                .map(PostListItemResponse::new)
                .toList();

        return new PostListResponse(posts, type, page, size, postPage.hasNext());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));
        return new PostResponse(post);
    }

    @Transactional
    public IdResponse updatePost(Long id, UpdatePostRequest request, Long userId) {
        PostValidator.validateUpdatePost(request);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));
        validatePostOwner(post, userId);
        post.update(request.title(), request.content());
        return new IdResponse(post.getId());
    }

    @Transactional
    public void deletePost(Long id, Long userId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));
        validatePostOwner(post, userId);
        post.softDelete();
    }

    private void validatePostOwner(Post post, Long userId) {
        if (!post.getUser().getId().equals(userId)) {
            throw new CustomException(PostErrorCode.POST_FORBIDDEN);
        }
    }

    @Transactional(readOnly = true)
    public PostSearchResponse searchPosts(String keyword, int page, int size) {
        PageValidator.validate(page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.searchByTitleWithUser(keyword, pageable);
        List<PostListItemResponse> posts = postPage.getContent()
                .stream()
                .map(PostListItemResponse::new)
                .toList();

        return new PostSearchResponse(posts, keyword, page, size, postPage.hasNext());
    }
}
