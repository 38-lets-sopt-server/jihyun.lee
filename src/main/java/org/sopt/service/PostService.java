package org.sopt.service;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.*;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.validation.PostValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // CREATE
    public CreatePostResponse createPost(CreatePostRequest request) {
        PostValidator.validateCreatePost(request);
        LocalDateTime createdAt = LocalDateTime.now();
        Post post = postRepository.save(new Post(
                postRepository.generateId(),
                request.title(),
                request.content(),
                request.author(),
                createdAt,
                request.boardType()
        ));
        return new CreatePostResponse(post.getId());
    }

    // READ - 전체 📝 과제
    public PostListResponse getAllPosts(BoardType boardType, int page, int size) {
        PostValidator.validatePageParams(page, size);

        List<PostListItemResponse> posts;
        long totalElements;

        if (boardType != null) {
            posts = postRepository.findAllByBoardType(boardType, page, size)
                    .stream()
                    .map(PostListItemResponse::new)
                    .toList();
            totalElements = postRepository.countByBoardType(boardType);
        } else {
            posts = postRepository.findAll(page, size)
                    .stream()
                    .map(PostListItemResponse::new)
                    .toList();
            totalElements = postRepository.countAll();
        }

        long totalPages = (long) Math.ceil((double) totalElements / size);
        boolean hasNext = page < totalPages - 1;

        return new PostListResponse(posts, boardType, page, size, hasNext);
    }

    // READ - 단건 📝 과제
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        return new PostResponse(post);
    }

    // UPDATE 📝 과제
    public UpdatePostResponse updatePost(Long id, UpdatePostRequest request) {
        PostValidator.validateUpdatePost(request);
        Post updatedPost = postRepository.update(id, request.title(), request.content())
                .orElseThrow(PostNotFoundException::new);
        return new UpdatePostResponse(updatedPost.getId());
    }

    // DELETE 📝 과제
    public void deletePost(Long id) {
        boolean deleted = postRepository.deleteById(id);

        if (!deleted) {
            throw new PostNotFoundException();
        }
    }
}
