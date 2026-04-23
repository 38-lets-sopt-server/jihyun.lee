package org.sopt.dto.response;

public class UpdatePostResponse {
    private final Long id;

    public UpdatePostResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
