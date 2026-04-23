package org.sopt.domain;

public class Post {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String createdAt;
    private BoardType boardType;

    public Post(Long id, String title, String content, String author, String createdAt, BoardType boardType) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.boardType = boardType;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public String getCreatedAt() { return createdAt; }
    public BoardType getBoardType() { return boardType; }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
