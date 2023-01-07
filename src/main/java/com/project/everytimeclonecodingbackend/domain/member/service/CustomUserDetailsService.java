package com.project.everytimeclonecodingbackend.domain.member.service;

import com.project.everytimeclonecodingbackend.domain.member.repository.MemberRepository;
import com.project.everytimeclonecodingbackend.global.exception.CustomException;
import com.project.everytimeclonecodingbackend.global.exception.ErrorCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() ->
                {
                    throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
                });
    }
}
