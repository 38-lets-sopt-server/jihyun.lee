package org.sopt.controller;

import org.sopt.domain.BoardType;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.*;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // POST /posts
    @PostMapping
    public ResponseEntity<ApiResponse<CreatePostResponse>> createPost(
            @RequestBody CreatePostRequest request
    ) {
        CreatePostResponse response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    // GET /posts
    @GetMapping
    public ApiResponse<PageResponse<PostListItemResponse>> getAllPosts(
            @RequestParam(required = false) BoardType boardType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.success(postService.getAllPosts(boardType, page, size));
    }

    // GET /posts/{id}
    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long id) {
        return ApiResponse.success(postService.getPost(id));
    }

    // PUT /posts/{id}
    @PutMapping("/{id}")
    public ApiResponse<UpdatePostResponse> updatePost(
            @PathVariable Long id,
            @RequestBody UpdatePostRequest request
    ) {
        return ApiResponse.success(postService.updatePost(id, request));
    }

    // DELETE /posts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
