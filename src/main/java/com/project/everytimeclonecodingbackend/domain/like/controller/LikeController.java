package com.project.everytimeclonecodingbackend.domain.like.controller;

import com.project.everytimeclonecodingbackend.domain.like.dto.LikeSaveRequestDto;
import com.project.everytimeclonecodingbackend.domain.like.dto.LikeSaveResponseDto;
import com.project.everytimeclonecodingbackend.domain.like.entity.Likes;
import com.project.everytimeclonecodingbackend.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping()
    public ResponseEntity<LikeSaveResponseDto> save(@RequestBody LikeSaveRequestDto likeSaveRequestDto, Authentication authentication) {
        Likes likes = likeService.save(likeSaveRequestDto.getPostId(), authentication);

        LikeSaveResponseDto likeSaveResponseDto = new LikeSaveResponseDto(
                likes.getId()
        );

        return ResponseEntity.ok(likeSaveResponseDto);
    }
}
