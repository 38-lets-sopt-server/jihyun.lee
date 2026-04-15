package org.sopt.dto.request;

public class UpdatePostRequest {
    public String title;
    public String content;

    public UpdatePostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
