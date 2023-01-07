package com.project.everytimeclonecodingbackend.domain.member.dto;

public class MemberLoginResponseDto {

    private String accessToken;

    public MemberLoginResponseDto() {
    }

    public MemberLoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
