package com.project.everytimeclonecodingbackend.domain.member.dto;

public class MemberSignupRequestDto {

    private String userId;
    private String password;
    private String nickname;
    private String name;
    private String school;
    private int admissionId;


    public MemberSignupRequestDto(String userId, String password, String nickname, String name, String  school, int admissionId) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.school = school;
        this.admissionId = admissionId;
    }

    public String getPassword() {
        return password;
    }

    public String getuserId() {
        return userId;
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
