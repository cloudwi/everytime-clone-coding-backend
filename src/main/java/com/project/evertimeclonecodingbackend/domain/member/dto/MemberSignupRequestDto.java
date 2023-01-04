package com.project.evertimeclonecodingbackend.domain.member.dto;

import com.project.evertimeclonecodingbackend.domain.member.entity.School;

public class MemberSignupRequestDto {

    private String id;
    private String password;
    private String nickname;
    private String name;
    private String school;
    private int admissionId;


    public MemberSignupRequestDto(String id, String password, String nickname, String name, String  school, int admissionId) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.school = school;
        this.admissionId = admissionId;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }

    public int getAdmissionId() {
        return admissionId;
    }
}
