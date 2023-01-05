package com.project.evertimeclonecodingbackend.domain.comment.entity;

import com.project.evertimeclonecodingbackend.domain.member.entity.Member;
import com.project.evertimeclonecodingbackend.domain.post.entity.Post;
import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String content;

    //연관관계
    @JoinColumn(name ="member_id")
    @ManyToOne
    private Member member;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post post;

    public Comment() {
    }

    public Comment(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Member getMember() {
        return member;
    }

    public Post getPost() {
        return post;
    }

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getComments().remove(this);
        }
        this.member = member;
        member.getComments().add(this);
    }

    public void setPost(Post post) {
        if (this.post != null) {
            this.post.getComments().remove(this);
        }
        this.post = post;
        post.getComments().add(this);
    }
}