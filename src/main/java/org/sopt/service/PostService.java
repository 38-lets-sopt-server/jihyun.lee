package org.sopt.service;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.*;
import org.sopt.exception.CustomException;
import org.sopt.exception.ErrorCode;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.sopt.validation.PostValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public IdResponse createPost(CreatePostRequest request) {
        PostValidator.validateCreatePost(request);

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

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
        PostValidator.validatePageParams(page, size);
        BoardType validatedBoardType = PostValidator.validateBoardType(boardType);

        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.findByBoardType(validatedBoardType, pageable);
        List<PostListItemResponse> posts = postPage.getContent()
                .stream()
                .map(PostListItemResponse::new)
                .toList();

        return new PostListResponse(posts, validatedBoardType, page, size, postPage.hasNext());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        return new PostResponse(post);
    }

    @Transactional
    public IdResponse updatePost(Long id, UpdatePostRequest request) {
        PostValidator.validateUpdatePost(request);
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        post.update(request.title(), request.content());
        return new IdResponse(post.getId());
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        post.softDelete();
    }
}
