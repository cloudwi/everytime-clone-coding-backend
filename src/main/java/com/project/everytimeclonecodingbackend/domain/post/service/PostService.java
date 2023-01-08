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
        long memberId = ((Member) authentication.getPrincipal()).getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!member.isEmailAuthentication()) {
            throw new CustomException(ErrorCode.NOT_CHECK_EMAIL);
        }

        Post post = new Post(title, content, Category.valueOf(category), anonymous);
        post.setMember(member);

        return postRepository.save(post);
    }

    public Page<Post> findAllByCategory(String category, PageRequest pageRequest, Authentication authentication) {
        validateCategory(category);

        long memberId = ((Member) authentication.getPrincipal()).getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!member.isEmailAuthentication() && !category.equals("자유게시판")) {
            throw new CustomException(ErrorCode.NOT_CHECK_EMAIL);
        }

        return postRepository.findAllByCategory(Category.valueOf(category), pageRequest);
    }

    public Page<Post> findAllByCategory(String category, PageRequest pageRequest) {
        validateCategory(category);

        return postRepository.findAllByCategory(Category.valueOf(category), pageRequest);
    }

    public Page<Post> search(Category category, Tag tag, String keyword, PageRequest pageRequest, Authentication authentication) {

        long memberId = ((Member) authentication.getPrincipal()).getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!member.isEmailAuthentication() && !category.equals("자유게시판")) {
            throw new CustomException(ErrorCode.NOT_CHECK_EMAIL);
        }

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
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }
    }


    @Transactional
    public void deleteById(long id, Authentication authentication) {
        long memberId = ((Member) authentication.getPrincipal()).getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!member.isEmailAuthentication()) {
            throw new CustomException(ErrorCode.NOT_CHECK_EMAIL);
        }

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (member.getId() != post.getMember().getId()) {
            throw new CustomException(ErrorCode.POST_NOT_DELETE);
        }

        postRepository.deleteById(id);
    }

    public Post findById(long id, Authentication authentication) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        long memberId = ((Member) authentication.getPrincipal()).getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!member.isEmailAuthentication() && !post.getCategory().toString().equals("자유게시판")) {
            throw new CustomException(ErrorCode.NOT_CHECK_EMAIL);
        }

        return post;
    }

    public boolean isDeletable(long id, Authentication authentication) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        long memberId = ((Member) authentication.getPrincipal()).getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!member.isEmailAuthentication()) {
            throw new CustomException(ErrorCode.NOT_CHECK_EMAIL);
        }

        return post.getMember().getId() == memberId;
    }

}
