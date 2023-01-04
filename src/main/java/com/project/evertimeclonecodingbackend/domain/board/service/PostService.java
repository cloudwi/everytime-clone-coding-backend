package com.project.evertimeclonecodingbackend.domain.board.service;

import com.project.evertimeclonecodingbackend.domain.board.dto.PostFindPageResponseDto;
import com.project.evertimeclonecodingbackend.domain.board.entity.Category;
import com.project.evertimeclonecodingbackend.domain.board.entity.Post;
import com.project.evertimeclonecodingbackend.domain.board.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public long save(String title, String content, String category) {
        Post post = new Post(title, content, Category.valueOf(category));
        return postRepository.save(post).getId();
    }

    public Page<Post> findPage(String category, PageRequest pageRequest) {
        return postRepository.findAllByCategory(Category.valueOf(category), pageRequest);
    }
}
