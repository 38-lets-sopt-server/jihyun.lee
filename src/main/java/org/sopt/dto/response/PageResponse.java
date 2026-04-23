package org.sopt.dto.response;

import java.util.List;

public class PageResponse<T> {
    private final List<T> content;
    private final int page;
    private final int size;
    private final boolean hasNext;

    public PageResponse(List<T> content, int page, int size, boolean hasNext ) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.hasNext = hasNext;
    }

    public List<T> getContent() { return content; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public boolean isHasNext() { return hasNext; }
}
