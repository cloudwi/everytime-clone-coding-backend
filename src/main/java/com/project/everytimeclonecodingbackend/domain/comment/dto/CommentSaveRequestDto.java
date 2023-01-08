package com.project.everytimeclonecodingbackend.domain.comment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentSaveRequestDto {
    private String content;
    private long postId;
    private boolean anonymous;
}
