package com.project.evertimeclonecodingbackend.domain.post.service;

import com.project.evertimeclonecodingbackend.domain.post.entity.Category;
import com.project.evertimeclonecodingbackend.domain.post.entity.Post;
import com.project.evertimeclonecodingbackend.domain.post.entity.Tag;
import com.project.evertimeclonecodingbackend.domain.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;

    @Test
    void save() {
    }

    @Test
    void findAllByCategory() {
    }

    @Test
    void search() {
        //given
        Category category = Category.비밀게시판;
        Tag tag = Tag.All;
        String keyword = "키워드";
        PageRequest pageRequest = PageRequest.of(0, 20);

        Post post1 = new Post("제목", "내용", Category.비밀게시판, true);
        Post post2 = new Post("제목", "내용 + 키워드", Category.비밀게시판, true);

        postRepository.save(post1);
        postRepository.save(post2);

        //when
        Page<Post> posts = postService.search(category, tag, keyword, pageRequest);

        //then
        posts.forEach(post -> System.out.println(post.getContent()));
    }
}