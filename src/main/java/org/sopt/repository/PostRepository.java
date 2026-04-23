package org.sopt.repository;

import org.sopt.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {
    private final List<Post> postList = new ArrayList<>();
    private Long nextId = 1L;

    public Post save(Post post) {
        postList.add(post);
        return post;
    }

    public List<Post> findAll(int page, int size) {
        return postList.stream()
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    public long countAll() {
        return postList.size();
    }

    public Optional<Post> findById(Long id) {
        return postList.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst();
    }

    public Optional<Post> update(Long id, String title, String content) {
        Optional<Post> optionalPost = findById(id);
        optionalPost.ifPresent(post -> post.update(title, content));
        return optionalPost;
    }

    public boolean deleteById(Long id) {
        return postList.removeIf(post -> post.getId().equals(id));
    }

    public Long generateId() {
        return nextId++;
    }
}