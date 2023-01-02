package com.project.evertimeclonecodingbackend.domain.member.controller;

import com.project.evertimeclonecodingbackend.domain.member.dto.JwtRequestDto;
import com.project.evertimeclonecodingbackend.domain.member.dto.JwtResponseDto;
import com.project.evertimeclonecodingbackend.domain.member.dto.MemberSignupRequestDto;
import com.project.evertimeclonecodingbackend.domain.member.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public String signup(@RequestBody MemberSignupRequestDto request) {
        return authService.signup(request);
    }

    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public JwtResponseDto login(@RequestBody JwtRequestDto jwtRequestDto) {
        try {
            return authService.login(jwtRequestDto);
        } catch (Exception e) {
            return new JwtResponseDto(e.getMessage());
        }
    }
}
