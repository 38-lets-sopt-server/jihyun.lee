package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
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
        String createdAt = LocalDateTime.now().toString();
        Post post = postRepository.save(new Post(
                postRepository.generateId(),
                request.title,
                request.content,
                request.author,
                createdAt
        ));
        return new CreatePostResponse(post.getId());
    }

    // READ - 전체 📝 과제
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostResponse::new)
                .toList();
    }

    // READ - 단건 📝 과제
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        return new PostResponse(post);
    }

    // UPDATE 📝 과제
    public void updatePost(Long id, UpdatePostRequest request) {
        PostValidator.validateUpdatePost(request);
        postRepository.update(id, request.title, request.content)
                .orElseThrow(PostNotFoundException::new);
    }

    // DELETE 📝 과제
    public void deletePost(Long id) {
        boolean deleted = postRepository.deleteById(id);

        if (!deleted) {
            throw new PostNotFoundException();
        }
    }
}
