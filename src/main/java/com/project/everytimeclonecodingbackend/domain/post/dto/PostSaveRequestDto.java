package com.project.everytimeclonecodingbackend.domain.post.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostSaveRequestDto {

    private String title;
    private String content;
    private String category;
    private boolean anonymous;
}
