package com.project.everytimeclonecodingbackend.domain.post.service;

import com.project.everytimeclonecodingbackend.domain.member.entity.Member;
import com.project.everytimeclonecodingbackend.domain.member.repository.MemberRepository;
import com.project.everytimeclonecodingbackend.domain.post.entity.Category;
import com.project.everytimeclonecodingbackend.domain.post.entity.Post;
import com.project.everytimeclonecodingbackend.domain.post.entity.Tag;
import com.project.everytimeclonecodingbackend.domain.post.repository.PostRepository;
import com.project.everytimeclonecodingbackend.global.exception.CustomException;
import com.project.everytimeclonecodingbackend.global.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Post save(String title, String content, String category, boolean anonymous, Authentication authentication) {
        Post post = new Post(title, content, Category.valueOf(category), anonymous);

        long memberId = ((Member) authentication.getPrincipal()).getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        post.setMember(member);

        return postRepository.save(post);
    }

    public Page<Post> findAllByCategory(String category, PageRequest pageRequest) {
        validateCategory(category);
        return postRepository.findAllByCategory(Category.valueOf(category), pageRequest);
    }

    public Page<Post> search(Category category, Tag tag, String keyword, PageRequest pageRequest) {
        return switch (tag) {
            case All -> postRepository.findAllByCategoryAndTitleContainingOrCommentsContaining(category, keyword, pageRequest);
            case TITLE -> postRepository.findAllByCategoryAndTitleContaining(category, keyword, pageRequest);
            case CONTENT -> postRepository.findAllByCategoryAndContentContaining(category, keyword, pageRequest);
        };
    }

    private void validateCategory(String category) {
        try {
            Category.valueOf(category);
        } catch (IllegalArgumentException e) {
            new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }
    }
}
