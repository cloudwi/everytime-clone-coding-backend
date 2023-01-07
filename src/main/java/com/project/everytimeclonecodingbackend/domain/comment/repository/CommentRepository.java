package com.project.everytimeclonecodingbackend.domain.comment.repository;

import com.project.everytimeclonecodingbackend.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
