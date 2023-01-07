package com.project.everytimeclonecodingbackend.domain.member.dto;

public class MemberSendEmailRequestDto {
    private String email;

    public MemberSendEmailRequestDto() {
    }

    public MemberSendEmailRequestDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
