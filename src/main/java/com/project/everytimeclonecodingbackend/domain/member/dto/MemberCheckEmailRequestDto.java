package com.project.everytimeclonecodingbackend.domain.member.dto;

public class MemberCheckEmailRequestDto {

    private String code;

    public MemberCheckEmailRequestDto() {
    }

    public MemberCheckEmailRequestDto(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
