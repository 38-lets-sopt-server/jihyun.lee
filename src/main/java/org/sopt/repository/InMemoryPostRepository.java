package org.sopt.repository;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryPostRepository implements PostRepository {
    private final List<Post> postList = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public Post save(Post post) {
        postList.add(post);
        return post;
    }

    @Override
    public List<Post> findAll(int page, int size) {
        return postList.stream()
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    @Override
    public List<Post> findAllByBoardType(BoardType boardType, int page, int size) {
        return postList.stream()
                .filter(post -> post.getBoardType() == boardType)
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    @Override
    public long countAll() {
        return postList.size();
    }

    @Override
    public long countByBoardType(BoardType boardType) {
        return postList.stream()
                .filter(post -> post.getBoardType() == boardType)
                .count();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postList.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Post> update(Long id, String title, String content) {
        Optional<Post> optionalPost = findById(id);
        optionalPost.ifPresent(post -> post.update(title, content));
        return optionalPost;
    }

    @Override
    public boolean deleteById(Long id) {
        return postList.removeIf(post -> post.getId().equals(id));
    }

    @Override
    public Long generateId() {
        return nextId++;
    }
}
