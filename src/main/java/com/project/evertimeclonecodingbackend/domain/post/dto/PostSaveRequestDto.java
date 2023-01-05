package com.project.evertimeclonecodingbackend.domain.post.dto;

public class PostSaveRequestDto {

    private String title;
    private String content;
    private String category;
    private boolean anonymous;

    public PostSaveRequestDto() {

    }

    public PostSaveRequestDto(String title, String content, String category, boolean anonymous) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.anonymous = anonymous;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getCategory() {
        return category;
    }



    public boolean isAnonymous() {
        return anonymous;
    }
}
