package com.project.everytimeclonecodingbackend.domain.post.repository;

import com.project.everytimeclonecodingbackend.domain.post.entity.Category;
import com.project.everytimeclonecodingbackend.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByCategory(Category category, PageRequest pageRequest);

    Page<Post> findAllByCategoryAndTitleContaining(Category category, String keyword, PageRequest pageRequest);

    Page<Post> findAllByCategoryAndContentContaining(Category category, String keyword, PageRequest pageRequest);

    @Query(value = "select p from Post p where p.category = ?1 and p.title like %?2% or p.content like %?2%")
    Page<Post> findAllByCategoryAndTitleContainingOrCommentsContaining(Category category, String keyword, PageRequest pageRequest);
}
