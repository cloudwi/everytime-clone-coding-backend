package com.project.evertimeclonecodingbackend.domain.member.controller;

import com.project.evertimeclonecodingbackend.domain.member.dto.EmailCheckRequestDto;
import com.project.evertimeclonecodingbackend.domain.member.dto.JwtRequestDto;
import com.project.evertimeclonecodingbackend.domain.member.dto.JwtResponseDto;
import com.project.evertimeclonecodingbackend.domain.member.dto.MemberSignupRequestDto;
import com.project.evertimeclonecodingbackend.domain.member.service.EmailService;
import com.project.evertimeclonecodingbackend.domain.member.service.MemberService;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;

    public MemberController(MemberService memberService, EmailService emailService) {
        this.memberService = memberService;
        this.emailService = emailService;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody MemberSignupRequestDto request) {
        return memberService.signup(request);
    }

    @PostMapping("/login")
    public JwtResponseDto login(@RequestBody JwtRequestDto jwtRequestDto) {
        try {
            return memberService.login(jwtRequestDto);
        } catch (Exception e) {
            return new JwtResponseDto(e.getMessage());
        }
    }

    @PostMapping("/email")
    public String emailCheck(@RequestBody EmailCheckRequestDto emailCheckRequestDto) throws MessagingException {
        String authCode = emailService.sendEmail(emailCheckRequestDto.getEmail());
        return authCode;
    }
}
