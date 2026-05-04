package org.sopt.repository;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(
            value = "select p from Post p join fetch p.user where p.boardType = :boardType",
            countQuery = "select count(p) from Post p where p.boardType = :boardType"
    )
    Page<Post> findByBoardTypeWithUser(
            @Param("boardType") BoardType boardType,
            Pageable pageable
    );

    @Query(
            value = "select p from Post p join fetch p.user where p.title like %:keyword%",
            countQuery = "select count(p) from Post p where p.title like %:keyword%"
    )
    Page<Post> searchByTitleWithUser(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
