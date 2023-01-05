package com.project.evertimeclonecodingbackend.domain.post.dto;

public class PostSaveRequestDto {

    private String title;
    private String content;
    private String category;

    public PostSaveRequestDto() {

    }

    public PostSaveRequestDto(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
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
}
