package com.project.everytimeclonecodingbackend.domain.comment.service;

import com.project.everytimeclonecodingbackend.domain.comment.entity.Comment;
import com.project.everytimeclonecodingbackend.domain.comment.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) { this.commentRepository = commentRepository; }
    @Transactional
    public long save(String content) {
        Comment comment = new Comment(content);
        return commentRepository.save(comment).getId();
    }
}
