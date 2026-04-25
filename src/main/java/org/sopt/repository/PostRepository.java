package org.sopt.repository;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    List<Post> findAll(int page, int size);
    List<Post> findAllByBoardType(BoardType boardType, int page, int size);
    long countAll();
    long countByBoardType(BoardType boardType);
    Optional<Post> findById(Long id);
    Optional<Post> update(Long id, String title, String content);
    boolean deleteById(Long id);
    Long generateId();
}
