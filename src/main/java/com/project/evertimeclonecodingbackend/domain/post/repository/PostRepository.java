package com.project.evertimeclonecodingbackend.domain.post.repository;

import com.project.evertimeclonecodingbackend.domain.post.entity.Category;
import com.project.evertimeclonecodingbackend.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByCategory(Category category, PageRequest pageRequest);

    Page<Post> findAllByCategoryAndTitleAndContentContaining(String category, String titleAndContent, PageRequest pageRequest);

    Page<Post> findAllByCategoryAndTitleContaining(String category, String title, PageRequest pageRequest);

    Page<Post> findAllByCategoryAndContentContaining(String category, String content, PageRequest pageRequest);
}
