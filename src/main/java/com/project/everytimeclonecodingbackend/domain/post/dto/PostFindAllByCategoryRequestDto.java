package com.project.everytimeclonecodingbackend.domain.post.dto;

public class PostFindAllByCategoryRequestDto {

    private String category;
    private int page;

    private boolean anonymous;
    public PostFindAllByCategoryRequestDto() {
    }

    public PostFindAllByCategoryRequestDto(String category, int page, boolean anonymous) {
        this.category = category;
        this.page = page;
        this.anonymous = anonymous;
    }

    public String getCategory() {
        return category;
    }

    public int getPage() {
        return page;
    }

    public boolean isAnonymous() {
        return anonymous;
    }
}
