package com.project.evertimeclonecodingbackend.domain.board.dto;

public class BoardSaveRequestDto {

    private String title;
    private String content;

    public BoardSaveRequestDto() {

    }

    public BoardSaveRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }
}
