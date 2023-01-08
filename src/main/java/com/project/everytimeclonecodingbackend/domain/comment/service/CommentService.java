package com.project.everytimeclonecodingbackend.domain.comment.service;

import com.project.everytimeclonecodingbackend.domain.comment.entity.Comment;
import com.project.everytimeclonecodingbackend.domain.comment.repository.CommentRepository;
import com.project.everytimeclonecodingbackend.domain.member.entity.Member;
import com.project.everytimeclonecodingbackend.domain.member.repository.MemberRepository;
import com.project.everytimeclonecodingbackend.domain.post.entity.Post;
import com.project.everytimeclonecodingbackend.domain.post.repository.PostRepository;
import com.project.everytimeclonecodingbackend.global.exception.CustomException;
import com.project.everytimeclonecodingbackend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public Comment save(String content, boolean isAnonymous, long postId, Authentication authentication) {

        long memberId = ((Member) authentication.getPrincipal()).getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!member.isEmailAuthentication()) {
            new CustomException(ErrorCode.NOT_CHECK_EMAIL);
        }

        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Comment comment = new Comment(content, isAnonymous, member, post);
        return commentRepository.save(comment);
    }
}
