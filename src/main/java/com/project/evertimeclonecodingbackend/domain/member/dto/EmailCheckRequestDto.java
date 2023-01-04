package com.project.evertimeclonecodingbackend.domain.member.dto;

public class EmailCheckRequestDto {
    private String email;

    public EmailCheckRequestDto() {
    }

    public EmailCheckRequestDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
