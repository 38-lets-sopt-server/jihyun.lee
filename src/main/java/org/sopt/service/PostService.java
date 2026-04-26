package org.sopt.service;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.*;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.sopt.validation.PostValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(
            PostRepository postRepository,
            UserRepository userRepository
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request) {
        PostValidator.validateCreatePost(request);
        LocalDateTime createdAt = LocalDateTime.now();

        User user = userRepository.findById(request.userId())
                .orElseThrow();

        Post post = new Post(
                request.title(),
                request.content(),
                user,
                createdAt,
                BoardType.valueOf(request.boardType())
        );
        return new CreatePostResponse(post.getId());
    }

    @Transactional(readOnly = true)
    public PostListResponse getAllPosts(String boardType, int page, int size) {
        PostValidator.validatePageParams(page, size);
        BoardType validatedBoardType = PostValidator.validateBoardType(boardType);

        List<PostListItemResponse> posts = postRepository.findByBoardType(validatedBoardType)
                .stream()
                .map(PostListItemResponse::new)
                .toList();

        long totalElements = postRepository.countByBoardType(validatedBoardType);
        long totalPages = (long) Math.ceil((double) totalElements / size);
        boolean hasNext = page < totalPages - 1;

        return new PostListResponse(posts, validatedBoardType, page, size, hasNext);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        return new PostResponse(post);
    }

    @Transactional
    public UpdatePostResponse updatePost(Long id, UpdatePostRequest request) {
        PostValidator.validateUpdatePost(request);
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        post.update(request.title(), request.content());
        return new UpdatePostResponse(post.getId());
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
