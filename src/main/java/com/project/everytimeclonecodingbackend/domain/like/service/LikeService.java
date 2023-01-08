package com.project.everytimeclonecodingbackend.domain.like.service;

import com.project.everytimeclonecodingbackend.domain.like.entity.Likes;
import com.project.everytimeclonecodingbackend.domain.like.repository.LikeRepository;
import com.project.everytimeclonecodingbackend.domain.member.entity.Member;
import com.project.everytimeclonecodingbackend.domain.member.repository.MemberRepository;
import com.project.everytimeclonecodingbackend.domain.post.entity.Post;
import com.project.everytimeclonecodingbackend.domain.post.repository.PostRepository;
import com.project.everytimeclonecodingbackend.global.exception.CustomException;
import com.project.everytimeclonecodingbackend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public Likes save(long postId, Authentication authentication) {
        long memberId = ((Member) authentication.getPrincipal()).getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!member.isEmailAuthentication()) {
            throw new CustomException(ErrorCode.NOT_CHECK_EMAIL);
        }

        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        member.getLikes().forEach(mLike -> post.getLikes().forEach(pLike->{
            if (mLike == pLike) {
                throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
            }}));
        Likes likes = new Likes();
        likes.setMember(member);
        likes.setPost(post);


        return likeRepository.save(likes);
    }
}
