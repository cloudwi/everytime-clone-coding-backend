package com.project.everytimeclonecodingbackend.domain.member.controller;

import com.project.everytimeclonecodingbackend.domain.member.dto.*;
import com.project.everytimeclonecodingbackend.domain.member.entity.Member;
import com.project.everytimeclonecodingbackend.domain.member.service.EmailService;
import com.project.everytimeclonecodingbackend.domain.member.service.MemberService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

import static java.rmi.server.LogStream.log;

@RestController
@RequestMapping("api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;
    private Logger log = LoggerFactory.getLogger(MemberController.class);

    public MemberController(MemberService memberService, EmailService emailService) {
        this.memberService = memberService;
        this.emailService = emailService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberSignupResponseDto> signup(@RequestBody MemberSignupRequestDto memberSignupRequestDto) {
        Member member = memberService.signup(
                memberSignupRequestDto.getuserId(),
                memberSignupRequestDto.getPassword(),
                memberSignupRequestDto.getNickname(),
                memberSignupRequestDto.getName(),
                memberSignupRequestDto.getSchool(),
                memberSignupRequestDto.getAdmissionId()
        );
        MemberSignupResponseDto memberSignupResponseDto = new MemberSignupResponseDto(
                member.getUserId()
        );
        return ResponseEntity.ok(memberSignupResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String accessToken = memberService.login(memberLoginRequestDto.getUserId(), memberLoginRequestDto.getPassword());
        MemberLoginResponseDto memberLoginResponseDto = new MemberLoginResponseDto(accessToken);
        return ResponseEntity.ok(memberLoginResponseDto);
    }

    @PostMapping("/email")
    public ResponseEntity<MemberSendEmailResponseDto> sendEmail(@RequestBody MemberSendEmailRequestDto emailCheckRequestDto, Authentication authentication) throws MessagingException, UnsupportedEncodingException {
        String authCode = emailService.sendEmail(emailCheckRequestDto.getEmail(), authentication);
        MemberSendEmailResponseDto memberSendEmailResponseDto = new MemberSendEmailResponseDto(authCode);
        return ResponseEntity.ok(memberSendEmailResponseDto);
    }

    @PostMapping("/email/check")
    public ResponseEntity<MemberCheckEmailResponseDto> checkEmail(@RequestBody MemberCheckEmailRequestDto memberCheckEmailRequestDto, Authentication authentication) {
        String userId = memberService.checkEmail(memberCheckEmailRequestDto.getCode(), authentication);

        MemberCheckEmailResponseDto memberCheckEmailResponseDto = new MemberCheckEmailResponseDto(userId);

        return ResponseEntity.ok(memberCheckEmailResponseDto);
    }
}
