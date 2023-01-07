package com.project.everytimeclonecodingbackend.domain.post.dto;

import java.time.LocalDateTime;

public class PostFindAllByCategoryResponseDto {

    private long id;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createTime;

    public PostFindAllByCategoryResponseDto() {
    }

    public PostFindAllByCategoryResponseDto(long id, String title, String content, String nickname, LocalDateTime createTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }
}
