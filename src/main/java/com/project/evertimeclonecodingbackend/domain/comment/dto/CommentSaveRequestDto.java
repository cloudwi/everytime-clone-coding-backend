package com.project.evertimeclonecodingbackend.domain.comment.dto;

import com.project.evertimeclonecodingbackend.domain.post.entity.Post;
import com.project.evertimeclonecodingbackend.domain.member.entity.Member;

public class CommentSaveRequestDto {
    private String content;
    private long memberId;
    private long postId;

    private CommentSaveRequestDto() {
    }

    public CommentSaveRequestDto(String content, long memberId, long postId) {
        this.content = content;
        this.memberId = memberId;
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public long getMemberId() {
        return memberId;
    }

    public long getPostId() {
        return postId;
    }
}
