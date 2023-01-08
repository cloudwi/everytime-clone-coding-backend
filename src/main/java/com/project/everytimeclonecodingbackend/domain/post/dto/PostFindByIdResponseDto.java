package com.project.everytimeclonecodingbackend.domain.post.dto;

import com.project.everytimeclonecodingbackend.domain.comment.dto.CommentFindAllResponseDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PostFindByIdResponseDto {
    private long id;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createTime;
    private boolean isDeletable;
    private List<CommentFindAllResponseDto> commentFindAllDtos;
    private int likeCount;
}
