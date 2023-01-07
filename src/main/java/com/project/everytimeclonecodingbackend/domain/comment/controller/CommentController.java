package com.project.everytimeclonecodingbackend.domain.comment.controller;

import com.project.everytimeclonecodingbackend.domain.comment.dto.CommentFindByPostResponseDto;
import com.project.everytimeclonecodingbackend.domain.comment.dto.CommentSaveRequestDto;
import com.project.everytimeclonecodingbackend.domain.comment.service.CommentService;
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
    public long save(@RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        return commentService.save(commentSaveRequestDto.getContent());
    }

    @GetMapping("/{postId}")
    public List<CommentFindByPostResponseDto> findByPost(@RequestParam("postId") long postId) {
        return null;
    }
}

