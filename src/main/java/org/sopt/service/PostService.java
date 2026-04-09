package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.repository.PostRepository;

import java.util.List;

public class PostService {
    private final PostRepository postRepository = new PostRepository();

    // CREATE
    public CreatePostResponse createPost(CreatePostRequest request) {
        if (request.title == null || request.title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다!");
        }
        if (request.content == null || request.content.isBlank()) {
            throw new IllegalArgumentException("내용은 필수입니다!");
        }
        String createdAt = java.time.LocalDateTime.now().toString();
        Post post = new Post(postRepository.generateId(), request.title, request.content, request.author, createdAt);
        postRepository.save(post);
        return new CreatePostResponse(post.getId(), "게시글 등록 완료!");
    }

    // READ - 전체 📝 과제
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(PostResponse::new).toList();
    }

    // READ - 단건 📝 과제
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
        }
        return new PostResponse(post);
    }

    // UPDATE 📝 과제
    public void updatePost(Long id, String newTitle, String newContent) {
        Post updatedPost = postRepository.update(id, newTitle, newContent);

        if (updatedPost == null) {
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
        }
    }

    // DELETE 📝 과제
    public void deletePost(Long id) {
        boolean deleted = postRepository.deleteById(id);

        if (!deleted) {
            throw new IllegalArgumentException("삭제할 게시글이 존재하지 않습니다.");
        }
    }
}
