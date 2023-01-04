package com.project.evertimeclonecodingbackend.domain.comment.repository;

import com.project.evertimeclonecodingbackend.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
