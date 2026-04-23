package org.sopt.dto.request;

import org.sopt.domain.BoardType;

public class CreatePostRequest {
    public String title;
    public String content;
    public String author;
    public BoardType boardType;

    public CreatePostRequest(String title, String content, String author, BoardType boardType) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.boardType = boardType;
    }
}
