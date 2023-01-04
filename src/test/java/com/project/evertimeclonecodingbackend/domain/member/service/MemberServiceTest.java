package com.project.evertimeclonecodingbackend.domain.member.service;

import com.project.evertimeclonecodingbackend.domain.member.dto.MemberSignupRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setup() {
        memberService.deleteAll();
    }

    @Test
    @DisplayName("회원 가입 시 아이디가 반환 됩니다.")
    void signup() {
        //given
        MemberSignupRequestDto memberSignupRequestDto = new MemberSignupRequestDto(
                "cloudwi",
                "MN77868!!!",
                "동글구름",
                "장주영",
                "동의대학교",
                17
        );

        //when
        String id = memberService.signup(memberSignupRequestDto);

        //then
        System.out.println("id : " + id);
        assertThat(id.isEmpty(), is(false));
    }
}