package com.project.evertimeclonecodingbackend.domain.member.dto;

public class MemberSignupRequestDto {

    private String id;
    private String password;
    private String nickname;

    public MemberSignupRequestDto(String id, String password, String nickname) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
}
