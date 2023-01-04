package com.project.evertimeclonecodingbackend.domain.member.entity;

import com.project.evertimeclonecodingbackend.domain.board.entity.Post;
import jakarta.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id
    private String id;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column(nullable = false)
    private int admissionId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private School school;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    //연관관계
    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();


    public Member() {

    }

    public Member(String id, String password, String nickname, int admissionId, School school, Role role) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.admissionId = admissionId;
        this.school = school;
        this.role = role;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public Role getRole() {
        return role;
    }

    public int getAdmissionId() {
        return admissionId;
    }

    public School getSchool() {
        return school;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        this.posts.add(post);
        if (post.getMember() != null) {
            post.setMember(this);
        }
    }
}
