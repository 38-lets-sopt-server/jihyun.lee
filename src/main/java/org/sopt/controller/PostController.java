package org.sopt.controller;

import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.service.PostService;

import java.util.List;

public class PostController {
    private final PostService postService = new PostService();

    // POST /posts
    public ApiResponse<Void> createPost(CreatePostRequest request) {
        try {
            postService.createPost(request);
            return ApiResponse.success("게시글 등록 완료!");
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    // GET /posts
    public ApiResponse<List<PostResponse>> getAllPosts() {
        return ApiResponse.success(postService.getAllPosts());
    }

    // GET /posts/{id}
    public ApiResponse<PostResponse> getPost(Long id) {
        try {
            return ApiResponse.success(postService.getPost(id));
        } catch (PostNotFoundException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    // PUT /posts/{id}
    public ApiResponse<Void> updatePost(Long id, String newTitle, String newContent) {
        try {
            postService.updatePost(id, newTitle, newContent);
            return ApiResponse.success("게시글 수정 완료!");
        } catch (PostNotFoundException | IllegalArgumentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    // DELETE /posts/{id}
    public ApiResponse<Void> deletePost(Long id) {
        try {
            postService.deletePost(id);
            return ApiResponse.success("게시글 삭제 완료!");
        } catch (PostNotFoundException e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}
