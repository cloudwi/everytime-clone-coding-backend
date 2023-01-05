package com.project.evertimeclonecodingbackend.domain.post.dto;

public class PostSearchRequestDto {

    private String category;
    private String tag;
    private String keyword;
    private int page;

    public PostSearchRequestDto(String category, String tag, String keyword, int page) {
        this.category = category;
        this.tag = tag;
        this.keyword = keyword;
        this.page = page;
    }

    public String getCategory() {
        return category;
    }

    public String getTag() {
        return tag;
    }

    public String getKeyword() {
        return keyword;
    }

    public int getPage() {
        return page;
    }
}
