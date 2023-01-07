package com.project.everytimeclonecodingbackend.domain.member.dto;

public class MemberSignupResponseDto {
    private String userId;

    public MemberSignupResponseDto() {
    }

    public MemberSignupResponseDto(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
