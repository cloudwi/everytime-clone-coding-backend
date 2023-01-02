package com.project.evertimeclonecodingbackend.domain.member.dto;

public class JwtResponseDto {

    private String accessToken;

    public JwtResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
