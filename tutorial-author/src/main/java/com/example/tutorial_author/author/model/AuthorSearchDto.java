package com.example.tutorial_author.author.model;


import com.example.tutorial_author.common.pagination.PageableRequest;

public class AuthorSearchDto {

    private PageableRequest pageable;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}
