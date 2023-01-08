package com.project.everytimeclonecodingbackend.domain.comment.controller;

import com.project.everytimeclonecodingbackend.domain.comment.dto.CommentFindByPostResponseDto;
import com.project.everytimeclonecodingbackend.domain.comment.dto.CommentSaveRequestDto;
import com.project.everytimeclonecodingbackend.domain.comment.dto.CommentSaveResponseDto;
import com.project.everytimeclonecodingbackend.domain.comment.entity.Comment;
import com.project.everytimeclonecodingbackend.domain.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping()
    public ResponseEntity save(@RequestBody CommentSaveRequestDto commentSaveRequestDto, Authentication authentication) {
        Comment comment = commentService.save(
                commentSaveRequestDto.getContent(),
                commentSaveRequestDto.isAnonymous(),
                commentSaveRequestDto.getPostId(),
                authentication
        );

        CommentSaveResponseDto commentSaveResponseDto = new CommentSaveResponseDto(
                comment.getId()
        );

        return ResponseEntity.ok(commentSaveResponseDto);
    }

    @GetMapping("/{postId}")
    public List<CommentFindByPostResponseDto> findByPost(@RequestParam("postId") long postId) {
        return null;
    }
}

