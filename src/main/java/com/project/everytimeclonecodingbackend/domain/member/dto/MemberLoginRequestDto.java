package com.project.everytimeclonecodingbackend.domain.member.dto;

public class MemberLoginRequestDto {

    private String userId;
    private String password;

    public MemberLoginRequestDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }


    public String getPassword() {
        return password;
    }
}

