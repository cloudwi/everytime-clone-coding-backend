package com.project.evertimeclonecodingbackend.domain.member.dto;

public class MemberLoginResponseDto {

    private String accessToken;

    public MemberLoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
