package com.project.evertimeclonecodingbackend.domain.member.dto;

public class JwtRequestDto {

    private String id;
    private String password;

    public JwtRequestDto(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }


    public String getPassword() {
        return password;
    }
}

