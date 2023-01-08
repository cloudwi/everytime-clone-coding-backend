package com.project.everytimeclonecodingbackend.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FreedomPostDto {
    private long id;
    private String title;
    private LocalDateTime createTime;
    private int likeCount;

}
