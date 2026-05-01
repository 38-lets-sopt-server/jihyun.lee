package org.sopt.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Post {

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
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    protected Post() {}

    public Post(String title, String content, User user, LocalDateTime createdAt, BoardType boardType) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
        this.boardType = boardType;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public User getUser() { return user; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public BoardType getBoardType() { return boardType; }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
