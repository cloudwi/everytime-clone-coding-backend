package com.project.everytimeclonecodingbackend.domain.member.dto;

public class MemberCheckEmailResponseDto {
    private String userId;

    public MemberCheckEmailResponseDto() {
    }

    public MemberCheckEmailResponseDto(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
