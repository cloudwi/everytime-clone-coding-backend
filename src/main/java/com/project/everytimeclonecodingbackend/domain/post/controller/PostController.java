package com.project.everytimeclonecodingbackend.domain.post.controller;

import com.project.everytimeclonecodingbackend.domain.comment.dto.CommentFindAllDto;
import com.project.everytimeclonecodingbackend.domain.post.dto.*;
import com.project.everytimeclonecodingbackend.domain.post.entity.Category;
import com.project.everytimeclonecodingbackend.domain.post.entity.Post;
import com.project.everytimeclonecodingbackend.domain.post.entity.Tag;
import com.project.everytimeclonecodingbackend.domain.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping()
    public ResponseEntity<PostSaveResponseDto> save(
            @RequestBody PostSaveRequestDto postSaveRequestDto,
            Authentication authentication
    ) {
        Post post = postService.save(
                postSaveRequestDto.getTitle(),
                postSaveRequestDto.getContent(),
                postSaveRequestDto.getCategory(),
                postSaveRequestDto.isAnonymous(),
                authentication
        );
        PostSaveResponseDto postSaveResponseDto = new PostSaveResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.isAnonymous() ? "익명" : post.getMember().getNickname(),
                post.getCreateTime());

        return ResponseEntity.ok(postSaveResponseDto);
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<PostFindAllByCategoryResponseDto>> findAllByCategory(@PathVariable String category, @RequestParam int page, Authentication authentication) {
        List<PostFindAllByCategoryResponseDto> postFindPageResponseDtos = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(page, 20, Sort.Direction.DESC, "createTime");

        postService.findAllByCategory(category, pageRequest, authentication).forEach(post -> {
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
    public ResponseEntity<List<PostFindByKeywordResponseDto>> search(@RequestBody PostSearchRequestDto postSearchRequestDto, Authentication authentication) {
        List<PostFindByKeywordResponseDto> postFindByKeywordResponseDtos = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(postSearchRequestDto.getPage(), 20);

        postService.search(
                Category.valueOf(postSearchRequestDto.getCategory()), 
                Tag.valueOf(postSearchRequestDto.getTag()), 
                postSearchRequestDto.getKeyword(), 
                pageRequest,
                authentication
        ).forEach(post -> {
            PostFindByKeywordResponseDto postFindByKeywordResponseDto = new PostFindByKeywordResponseDto(
                    post.getId(),
                    post.getTitle(),
                    post.getContent()
            );

            postFindByKeywordResponseDtos.add(postFindByKeywordResponseDto);
        });
        return ResponseEntity.ok(postFindByKeywordResponseDtos);
    }

    @GetMapping("/{category}/{id}")
    public ResponseEntity<PostFindByIdResponseDto> findById(@PathVariable String category, @PathVariable long id, Authentication authentication) {
        Post post = postService.findById(id, authentication);
        boolean isDeletable = postService.isDeletable(id, authentication);

        List<CommentFindAllDto> commentFindAllDtos = new ArrayList<>();
        post.getComments().forEach(comment -> {
            CommentFindAllDto commentFindAllDto = new CommentFindAllDto(
                    comment.getId(),
                    comment.getContent(),
                    comment.isAnonymous() ? "익명" : comment.getMember().getNickname(),
                    comment.getCreateTime()
            );
            commentFindAllDtos.add(commentFindAllDto);
        });

        PostFindByIdResponseDto postFindByIdResponseDto = new PostFindByIdResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.isAnonymous() ? "익명" : post.getMember().getNickname(),
                post.getCreateTime(),
                isDeletable,
                commentFindAllDtos
        );

        return ResponseEntity.ok(postFindByIdResponseDto);
    }

    @DeleteMapping()
    public ResponseEntity deleteById(@RequestBody PostDeleteRequestDto postDeleteRequestDto, Authentication authentication) {
        postService.deleteById(postDeleteRequestDto.getId(), authentication);
        return ResponseEntity.ok().body(null);
    }
}
