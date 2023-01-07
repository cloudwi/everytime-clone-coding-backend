package com.project.everytimeclonecodingbackend.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class PostSaveResponseDto {

    private long postId;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createTime;
}
