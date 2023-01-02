package com.project.evertimeclonecodingbackend.domain.member.service;

import com.project.evertimeclonecodingbackend.domain.member.dto.JwtRequestDto;
import com.project.evertimeclonecodingbackend.domain.member.dto.JwtResponseDto;
import com.project.evertimeclonecodingbackend.domain.member.dto.MemberSignupRequestDto;
import com.project.evertimeclonecodingbackend.domain.member.entity.Member;
import com.project.evertimeclonecodingbackend.domain.member.entity.Role;
import com.project.evertimeclonecodingbackend.domain.member.repository.MemberRepository;
import com.project.evertimeclonecodingbackend.domain.member.service.impl.UserDetailsImpl;
import com.project.evertimeclonecodingbackend.global.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String signup(MemberSignupRequestDto request) {
        boolean existMember = memberRepository.existsById(request.getId());

        if (existMember) {
            throw new IllegalArgumentException("중복된 아이디 입니다.");
        }

        Member member = new Member(request.getId(), request.getPassword(), request.getNickname(), Role.USER);
        member.encryptPassword(passwordEncoder);

        memberRepository.save(member);
        return member.getId();
    }

    public JwtResponseDto login(JwtRequestDto jwtRequestDto) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequestDto.getId(), jwtRequestDto.getPassword())
        );

        return createJwtToken(authentication);
    }

    private JwtResponseDto createJwtToken(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(principal);
        return new JwtResponseDto(token);
    }
}
