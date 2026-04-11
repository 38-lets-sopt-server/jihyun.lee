package org.sopt.dto.request;

public class UpdatePostRequest {
    public Long id;
    public String title;
    public String content;

    public UpdatePostRequest(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
