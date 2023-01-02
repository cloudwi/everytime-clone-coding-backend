package com.project.evertimeclonecodingbackend.domain.member.entity;

import jakarta.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
public class Member {

    @Id
    private String id;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Member() {

    }

    public Member(String id, String password, String nickname, Role role) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
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
}
