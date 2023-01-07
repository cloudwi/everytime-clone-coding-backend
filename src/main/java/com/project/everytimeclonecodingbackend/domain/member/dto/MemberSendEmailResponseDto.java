package com.project.everytimeclonecodingbackend.domain.member.dto;

public class MemberSendEmailResponseDto {
    private String code;

    public MemberSendEmailResponseDto() {
    }

    public MemberSendEmailResponseDto(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
