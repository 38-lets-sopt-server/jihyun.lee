package org.sopt.repository;

import org.sopt.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);
}
