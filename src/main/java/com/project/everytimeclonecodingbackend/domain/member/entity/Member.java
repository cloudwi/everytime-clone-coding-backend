package com.project.everytimeclonecodingbackend.domain.member.entity;

import com.project.everytimeclonecodingbackend.domain.post.entity.Post;
import com.project.everytimeclonecodingbackend.domain.comment.entity.Comment;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String userId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int admissionId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private School school;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Column
    private boolean emailAuthentication;
    @Column
    private String emailAuthenticationCode;

    //연관관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Member() {
    }

    public Member(String userId, String password, String nickname, String name, String school, int admissionId) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.admissionId = admissionId;
        this.school = School.valueOf(school);
        this.role = Role.ROLE_USER;
        this.emailAuthentication = false;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }

    public long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(role.name()));
        return auth;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
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

    public boolean isEmailAuthentication() {
        return emailAuthentication;
    }

    public String getEmailAuthenticationCode() {
        return emailAuthenticationCode;
    }

    public void setEmailAuthenticationCode(String emailAuthenticationCode) {
        this.emailAuthenticationCode = emailAuthenticationCode;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addPost(Post post) {
        this.posts.add(post);
        if (post.getMember() != null) {
            post.setMember(this);
        }
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        if (comment.getMember() != null) {
            comment.setMember(this);
        }
    }

    public void checkEmail() {
        this.emailAuthentication = true;
    }
}
