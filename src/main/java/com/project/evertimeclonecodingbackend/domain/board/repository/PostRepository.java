package com.project.evertimeclonecodingbackend.domain.board.repository;

import com.project.evertimeclonecodingbackend.domain.board.entity.Category;
import com.project.evertimeclonecodingbackend.domain.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByCategory(Category category, PageRequest pageRequest);
}
