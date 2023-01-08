package com.project.everytimeclonecodingbackend.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberProfileResponseDto {

    private String nickname;
    private String name;
    private String userId;
    private String school;
    private int admissionId;
}
