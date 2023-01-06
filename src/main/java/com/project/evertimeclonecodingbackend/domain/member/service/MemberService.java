package com.project.evertimeclonecodingbackend.domain.member.service;

import com.project.evertimeclonecodingbackend.domain.member.entity.Member;
import com.project.evertimeclonecodingbackend.domain.member.repository.MemberRepository;
import com.project.evertimeclonecodingbackend.global.exception.CustomException;
import com.project.evertimeclonecodingbackend.global.exception.ErrorCode;
import com.project.evertimeclonecodingbackend.global.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public Member signup(
            String userId,
            String password,
            String nickname,
            String name,
            String school,
            int admissionId
    ) {
        validateMatchedUserId(userId);
        Member member = new Member(userId, passwordEncoder.encode(password), nickname, name, school, admissionId);
        memberRepository.save(member);
        return member;
    }

    public String login(String userId, String password) {
        Member findMember = findMember(userId);
        validateMatchedPassword(password, findMember.getPassword());
        String accessToken = jwtTokenProvider.createAccessToken(findMember.getUserId(), findMember.getRole().name());
        return "bearer " + accessToken;
    }

    @Transactional
    public void deleteAll() {
        memberRepository.deleteAll();
    }

    public String checkEmail(String code, Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();

        if (member.getEmailAuthenticationCode().equals(code) && member.isEmailAuthentication() == false) {
            member.checkEmail();
            return member.getUserId();
        } else {
            throw new IllegalArgumentException("이미 이메일 인증을 하셨거나, 유효한 코드가 아닙니다.");
        }
    }

    private void validateMatchedUserId(String userId) {
        if (memberRepository.findByUserId(userId).isPresent()) {
            throw new CustomException(ErrorCode.USER_ID_DUPLICATE);
        }
    }

    private Member findMember(String userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(()->{
                    throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
                });
    }

    private void validateMatchedPassword(String validPassword, String memberPassword) {
        if (!passwordEncoder.matches(validPassword, memberPassword)) {
            throw new CustomException(ErrorCode.PASSWORD_DIFFERENT);
        }
    }
}
