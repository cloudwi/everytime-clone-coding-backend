package com.project.evertimeclonecodingbackend.domain.post.entity;

import com.project.evertimeclonecodingbackend.domain.comment.entity.Comment;
import com.project.evertimeclonecodingbackend.domain.member.entity.Member;
import com.project.evertimeclonecodingbackend.global.entity.BaseTimeEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String title;
    @Column(nullable = false)
    @Lob
    private String content;
    @Column(nullable = false)
    private Category category;

    @Column
    private boolean anonymous;

    //연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    public Post() {
    }

    public Post(String title, String content, Category category, boolean anonymous) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.anonymous = anonymous;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public long getId() {
        return this.id;
    }

    public Member getMember() {
        return this.member;
    }

    public Category getCategory() {
        return category;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getPosts().remove(this);
        }
        this.member = member;
        member.getPosts().add(this);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        if (comment.getPost() != null) {
            comment.setPost(this);
        }
    }
}