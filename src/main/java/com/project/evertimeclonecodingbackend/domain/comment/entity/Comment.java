package com.project.evertimeclonecodingbackend.domain.comment.entity;

import com.project.evertimeclonecodingbackend.domain.board.entity.Post;
import com.project.evertimeclonecodingbackend.domain.member.entity.Member;

import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String content;

    @JoinColumn(name ="member_id")
    @ManyToOne
    private Member member;

    @JoinColumn(name ="post_id")
    @ManyToOne
    private Post post;

    public Comment(String content) { this.content = content; }

    public long getId() { return this.id; }

    public Member getMember() {
        return this.member;
    }
}