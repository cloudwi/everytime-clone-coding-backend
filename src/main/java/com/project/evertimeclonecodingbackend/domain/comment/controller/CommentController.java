package com.project.evertimeclonecodingbackend.domain.comment.controller;

import com.project.evertimeclonecodingbackend.domain.comment.dto.CommentSaveDto;
import com.project.evertimeclonecodingbackend.domain.comment.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) { this.commentService=commentService; }
    @PostMapping()
    public long save(@RequestBody CommentSaveDto commentSaveDto) {
        return commentService.save(commentSaveDto.getContent());
    }
}

