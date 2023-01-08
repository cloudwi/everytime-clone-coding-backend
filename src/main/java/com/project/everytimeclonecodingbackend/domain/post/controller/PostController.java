package com.project.everytimeclonecodingbackend.domain.post.controller;

import com.project.everytimeclonecodingbackend.domain.comment.dto.CommentFindAllResponseDto;
import com.project.everytimeclonecodingbackend.domain.comment.service.CommentService;
import com.project.everytimeclonecodingbackend.domain.post.dto.*;
import com.project.everytimeclonecodingbackend.domain.post.entity.Category;
import com.project.everytimeclonecodingbackend.domain.post.entity.Post;
import com.project.everytimeclonecodingbackend.domain.post.entity.Tag;
import com.project.everytimeclonecodingbackend.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

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

        List<CommentFindAllResponseDto> commentFindAllDtos = new ArrayList<>();
        post.getComments().forEach(comment -> {
            CommentFindAllResponseDto commentFindAllDto = new CommentFindAllResponseDto(
                    comment.getId(),
                    comment.getContent(),
                    comment.isAnonymous() ? "익명" : comment.getMember().getNickname(),
                    comment.getCreateTime(),
                    commentService.isDeletable(comment.getId(),authentication)
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
                commentFindAllDtos,
                post.getLikes().size()
        );

        return ResponseEntity.ok(postFindByIdResponseDto);
    }

    @DeleteMapping()
    public ResponseEntity deleteById(@RequestBody PostDeleteRequestDto postDeleteRequestDto, Authentication authentication) {
        postService.deleteById(postDeleteRequestDto.getId(), authentication);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/main")
    public ResponseEntity<PostFindMainResponseDto> mainPost() {
        PageRequest pageRequest = PageRequest.of(0, 4, Sort.Direction.DESC, "createTime");

        Page<Post> freedomPosts = postService.findAllByCategory("자유게시판", pageRequest);
        Page<Post> secretPosts = postService.findAllByCategory("비밀게시판", pageRequest);
        Page<Post> graduatePosts = postService.findAllByCategory("졸업생게시판", pageRequest);
        Page<Post> freshmanPosts = postService.findAllByCategory("새내기게시판", pageRequest);
        Page<Post> issuePosts = postService.findAllByCategory("시사_이슈", pageRequest);
        Page<Post> marketplacePosts = postService.findAllByCategory("장터게시판", pageRequest);

        List<FreedomPostDto> freedomPostsDtos = new ArrayList<>();
        freedomPosts.forEach(post -> {
            FreedomPostDto freedomPostDto = new FreedomPostDto(
                    post.getId(),
                    post.getTitle(),
                    post.getCreateTime(),
                    post.getLikes().size()
            );

            freedomPostsDtos.add(freedomPostDto);
        });

        List<SecretPostDto> secretPostDtos = new ArrayList<>();
        secretPosts.forEach(post -> {
            SecretPostDto secretPostsDto = new SecretPostDto(
                    post.getId(),
                    post.getContent(),
                    post.getCreateTime(),
                    post.getLikes().size()
            );

            secretPostDtos.add(secretPostsDto);
        });

        List<GraduatePostDto> graduatePostDtos = new ArrayList<>();
        graduatePosts.forEach(post -> {
            GraduatePostDto graduatePostDto = new GraduatePostDto(
                    post.getId(),
                    post.getContent(),
                    post.getCreateTime(),
                    post.getLikes().size()
            );

            graduatePostDtos.add(graduatePostDto);
        });

        List<FreshmanPostDto> freshmanPostDtos = new ArrayList<>();
        freshmanPosts.forEach(post -> {
            FreshmanPostDto freshmanPostDto = new FreshmanPostDto(
                    post.getId(),
                    post.getContent(),
                    post.getCreateTime(),
                    post.getLikes().size()
            );

            freshmanPostDtos.add(freshmanPostDto);
        });

        List<IssuePostDto> issuePostDtos = new ArrayList<>();
        issuePosts.forEach(post -> {
            IssuePostDto issuePostDto = new IssuePostDto(
                    post.getId(),
                    post.getContent(),
                    post.getCreateTime(),
                    post.getLikes().size()
            );

            issuePostDtos.add(issuePostDto);
        });

        List<MarketplacePostDto> marketplacePostDtos = new ArrayList<>();
        issuePosts.forEach(post -> {
            MarketplacePostDto marketplacePostDto = new MarketplacePostDto(
                    post.getId(),
                    post.getContent(),
                    post.getCreateTime(),
                    post.getLikes().size()
            );

            marketplacePostDtos.add(marketplacePostDto);
        });

        PostFindMainResponseDto postFindMainResponseDto = new PostFindMainResponseDto(
                freedomPostsDtos,
                secretPostDtos,
                graduatePostDtos,
                freshmanPostDtos,
                issuePostDtos,
                marketplacePostDtos
        );

        return ResponseEntity.ok(postFindMainResponseDto);
    }
}
