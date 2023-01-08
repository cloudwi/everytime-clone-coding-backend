package com.project.everytimeclonecodingbackend.domain.comment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentFindAllResponseDto {
    private long id;
    private String content;
    private String nickname;
    private LocalDateTime createTime;
    private boolean deletable;
}
