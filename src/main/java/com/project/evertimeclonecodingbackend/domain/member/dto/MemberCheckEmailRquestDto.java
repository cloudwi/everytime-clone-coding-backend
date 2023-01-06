package com.project.evertimeclonecodingbackend.domain.member.dto;

public class MemberCheckEmailRquestDto {

    private String code;

    public MemberCheckEmailRquestDto() {
    }

    public MemberCheckEmailRquestDto(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
