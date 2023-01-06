package com.project.evertimeclonecodingbackend.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.evertimeclonecodingbackend.domain.member.dto.MemberLoginRequestDto;
import com.project.evertimeclonecodingbackend.domain.member.dto.MemberSendEmailRequestDto;
import com.project.evertimeclonecodingbackend.domain.member.dto.MemberSignupRequestDto;
import com.project.evertimeclonecodingbackend.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberService memberService;
    Logger log = LoggerFactory.getLogger(MemberControllerTest.class);

    @BeforeEach
    void setup() {
        memberService.deleteAll();
    }

    @Test
    @DisplayName("회원 가입을 성공하면 id가 반환됩니다.")
    void signupTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MemberSignupRequestDto memberSignupRequestDto = new MemberSignupRequestDto(
                "cloudwi",
                "testPassword",
                "동글구름",
                "장주영",
                "동의대학교",
                17
        );

        mockMvc.perform(
                        post("/api/v1/member/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(objectMapper.writeValueAsString(memberSignupRequestDto))
                )
                .andExpect(status().isOk())
                .andDo(
                        document("MemberController/signup",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("아이디"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("school").type(JsonFieldType.STRING).description("학교"),
                                        fieldWithPath("admissionId").type(JsonFieldType.NUMBER).description("학번")
                                ),
                                responseFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("아이디")
                                )
                        )
                );
    }

    @Test
    @DisplayName("정상 로그인 시 토큰이 반환 됩니다.")
    void loginTest() throws Exception {
        signup();

        ObjectMapper objectMapper = new ObjectMapper();
        MemberLoginRequestDto memberLoginRequestDto = new MemberLoginRequestDto(
                "cloudwi",
                "testPassword"
        );

        mockMvc.perform(
                        post("/api/v1/member/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(objectMapper.writeValueAsString(memberLoginRequestDto))
                )
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "MemberController/login",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("아이디"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("토큰")
                                )
                        )
                );
    }

    @Test
    @DisplayName("이메일 인증 api는 정상 작동하면 인증 코드가 반환된다.")
    void emailTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        signup();
        String accessToken = login();

        MemberSendEmailRequestDto memberSendEmailRequestDto = new MemberSendEmailRequestDto(
                "cloudwi@naver.com"
        );

        mockMvc.perform(
                        post("/api/v1/member/email")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .header("AccessToken", accessToken)
                                .content(objectMapper.writeValueAsString(memberSendEmailRequestDto))
                )
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "MemberController/email",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("AccessToken").description("토큰")
                                ),
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("인증코드")
                                )
                        )
                );
    }


    private void signup() {
        MemberSignupRequestDto memberSignupRequestDto = new MemberSignupRequestDto(
                "cloudwi",
                "testPassword",
                "동글구름",
                "장주영",
                "동의대학교",
                17
        );
        memberService.signup(
                memberSignupRequestDto.getuserId(),
                memberSignupRequestDto.getPassword(),
                memberSignupRequestDto.getNickname(),
                memberSignupRequestDto.getName(),
                memberSignupRequestDto.getSchool(),
                memberSignupRequestDto.getAdmissionId()
        );

        log.info("회원가입 완료");
    }

    private String login() {

        MemberLoginRequestDto memberLoginRequestDto = new MemberLoginRequestDto(
                "cloudwi",
                "testPassword"
        );
        String accessToken = memberService.login(
                memberLoginRequestDto.getUserId(),
                memberLoginRequestDto.getPassword()
        );

        log.info("로그인 완료");

        return accessToken;
    }
}