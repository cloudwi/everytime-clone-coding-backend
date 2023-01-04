package com.project.evertimeclonecodingbackend.domain.board.controller;

import com.project.evertimeclonecodingbackend.domain.board.dto.PostSaveRequestDto;
import com.project.evertimeclonecodingbackend.domain.board.dto.PostFindPageResponseDto;
import com.project.evertimeclonecodingbackend.domain.board.service.PostService;
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
        return postService.save(postSaveRequestDto.getTitle(), postSaveRequestDto.getContent(), postSaveRequestDto.getCategory());
    }

    @GetMapping("/{category}/{page}")
    public ResponseEntity<List<PostFindPageResponseDto>> findPage(@PathVariable String category, @PathVariable int page) {
        List<PostFindPageResponseDto> postFindPageResponseDtos = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(page, 20);
        postService.findPage(category, pageRequest).forEach(post -> {
            PostFindPageResponseDto postFindPageResponseDto = new PostFindPageResponseDto(

            );

            postFindPageResponseDtos.add(postFindPageResponseDto);
        });
        return ResponseEntity.ok(postFindPageResponseDtos);
    }
}
