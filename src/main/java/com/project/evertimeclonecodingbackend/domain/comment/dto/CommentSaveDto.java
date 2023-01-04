package com.project.evertimeclonecodingbackend.domain.comment.dto;

import com.project.evertimeclonecodingbackend.domain.board.entity.Post;
import com.project.evertimeclonecodingbackend.domain.member.entity.Member;

public class CommentSaveDto {
    private long id;
    private String content;
    private Member member;
    private Post post;

    public CommentSaveDto(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getContent() { return this.content; }
}
