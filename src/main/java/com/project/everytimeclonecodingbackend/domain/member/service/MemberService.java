package com.project.everytimeclonecodingbackend.domain.member.service;

import com.project.everytimeclonecodingbackend.domain.member.entity.Member;
import com.project.everytimeclonecodingbackend.domain.member.repository.MemberRepository;
import com.project.everytimeclonecodingbackend.global.exception.CustomException;
import com.project.everytimeclonecodingbackend.global.exception.ErrorCode;
import com.project.everytimeclonecodingbackend.global.security.JwtTokenProvider;
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
        return "Bearer " + accessToken;
    }

    @Transactional
    public void deleteAll() {
        memberRepository.deleteAll();
    }

    @Transactional
    public String checkEmail(String code, Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        member = memberRepository.findById(member.getId()).get();
        if (member.getEmailAuthenticationCode().equals(code) && member.isEmailAuthentication() == false) {
            member.checkEmail();
            return member.getUserId();
        } else {
            throw new CustomException(ErrorCode.EMAIL_CHECK_DUPLICATE);
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
