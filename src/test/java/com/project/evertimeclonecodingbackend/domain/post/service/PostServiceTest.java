package com.project.evertimeclonecodingbackend.domain.post.service;

import com.project.evertimeclonecodingbackend.domain.post.dto.PostSaveRequestDto;
import com.project.evertimeclonecodingbackend.domain.post.entity.Category;
import com.project.evertimeclonecodingbackend.domain.post.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("게시글을 생성할 수 있습니다.")
    void save() {
        //given
        PostSaveRequestDto postSaveRequestDto = new PostSaveRequestDto(
                "자유게시판 제목임",
                "자유게시판 내용임",
                "자유게시판"
        );

        //when
        long id = postService.save(
                postSaveRequestDto.getTitle(),
                postSaveRequestDto.getContent(),
                postSaveRequestDto.getCategory()
        );

        //then
        System.out.println("id = " + id);
        assertThat(id,is(id));

    }

    @Test
    void findPage() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 20);
        Category category = Category.비밀게시판;

        //when
        Page<Post> postPage = postService.findPage(String.valueOf(category), pageRequest);

        //then
        postPage.get().forEach(post -> System.out.println(post.getTitle()));
    }
}