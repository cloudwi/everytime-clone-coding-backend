package com.project.evertimeclonecodingbackend.domain.comment.controller;

import com.project.evertimeclonecodingbackend.domain.comment.dto.CommentFindByPostResponseDto;
import com.project.evertimeclonecodingbackend.domain.comment.dto.CommentSaveRequestDto;
import com.project.evertimeclonecodingbackend.domain.comment.service.CommentService;
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

