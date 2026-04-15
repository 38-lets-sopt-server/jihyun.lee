package org.sopt.controller;

import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService = new PostService();

    // POST /posts
    @PostMapping
    public ApiResponse<Void> createPost(@RequestBody CreatePostRequest request) {
        try {
            postService.createPost(request);
            return ApiResponse.successMessage("✅ 게시글 등록 완료!");
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    // GET /posts 📝 과제
    @GetMapping
    public ApiResponse<List<PostResponse>> getAllPosts() {
        return ApiResponse.success(postService.getAllPosts());
    }

    // GET /posts/{id} 📝 과제
    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long id) {
        try {
            return ApiResponse.success(postService.getPost(id));
        } catch (PostNotFoundException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    // PUT /posts/{id} 📝 과제
    @PutMapping("/{id}")
    public ApiResponse<Void> updatePost(
            @PathVariable Long id,
            @RequestBody UpdatePostRequest request
    ) {
        try {
            postService.updatePost(id, request);
            return ApiResponse.successMessage("✅ 게시글 수정 완료!");
        } catch (PostNotFoundException | IllegalArgumentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    // DELETE /posts/{id} 📝 과제
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return ApiResponse.successMessage("✅ 게시글 삭제 완료!");
        } catch (PostNotFoundException e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}
