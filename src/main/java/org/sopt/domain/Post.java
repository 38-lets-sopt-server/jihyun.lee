package org.sopt.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@SQLRestriction("deleted_at IS NULL")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private BoardType boardType;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    protected Post() {}

    public Post(String title, String content, User user, BoardType boardType) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.boardType = boardType;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public User getUser() { return user; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public BoardType getBoardType() { return boardType; }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}
