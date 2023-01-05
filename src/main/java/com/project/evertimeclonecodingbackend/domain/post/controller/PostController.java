package com.project.evertimeclonecodingbackend.domain.post.controller;

import com.project.evertimeclonecodingbackend.domain.post.dto.*;
import com.project.evertimeclonecodingbackend.domain.post.entity.Category;
import com.project.evertimeclonecodingbackend.domain.post.entity.Tag;
import com.project.evertimeclonecodingbackend.domain.post.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/board")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping()
    public long save(@RequestBody PostSaveRequestDto postSaveRequestDto) {
        return postService.save(postSaveRequestDto.getTitle(), postSaveRequestDto.getContent(), postSaveRequestDto.getCategory(), postSaveRequestDto.isAnonymous());
    }

    @GetMapping()
    public ResponseEntity<List<PostFindAllByCategoryResponseDto>> findAllByCategory(@RequestBody PostFindAllByCategoryRequestDto postFindAllByCategoryRequestDto) {
        List<PostFindAllByCategoryResponseDto> postFindPageResponseDtos = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(postFindAllByCategoryRequestDto.getPage(), 20);

        postService.findAllByCategory(postFindAllByCategoryRequestDto.getCategory(), pageRequest).forEach(post -> {
            PostFindAllByCategoryResponseDto postFindPageResponseDto = new PostFindAllByCategoryResponseDto(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.isAnonymous() ? "익명" : post.getMember().getNickname(),
                    post.getCreateTime()
            );

            postFindPageResponseDtos.add(postFindPageResponseDto);
        });
        return ResponseEntity.ok(postFindPageResponseDtos);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostFindByKeywordResponseDto>> search(@RequestBody PostSearchRequestDto postSearchRequestDto) {
        List<PostFindByKeywordResponseDto> postFindByKeywordResponseDtos = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(postSearchRequestDto.getPage(), 20);

        postService.search(Category.valueOf(postSearchRequestDto.getCategory()), Tag.valueOf(postSearchRequestDto.getTag()), postSearchRequestDto.getKeyword(), pageRequest).forEach(post -> {
                            PostFindByKeywordResponseDto postFindByKeywordResponseDto = new PostFindByKeywordResponseDto(
                                    post.getId(),
                                    post.getTitle(),
                                    post.getContent()
                            );

            postFindByKeywordResponseDtos.add(postFindByKeywordResponseDto);
        });
        return ResponseEntity.ok(postFindByKeywordResponseDtos);
    }
}
