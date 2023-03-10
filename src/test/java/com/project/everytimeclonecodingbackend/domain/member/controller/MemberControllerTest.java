package com.project.everytimeclonecodingbackend.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.everytimeclonecodingbackend.domain.member.dto.MemberCheckEmailRequestDto;
import com.project.everytimeclonecodingbackend.domain.member.dto.MemberLoginRequestDto;
import com.project.everytimeclonecodingbackend.domain.member.dto.MemberSendEmailRequestDto;
import com.project.everytimeclonecodingbackend.domain.member.dto.MemberSignupRequestDto;
import com.project.everytimeclonecodingbackend.domain.member.entity.Member;
import com.project.everytimeclonecodingbackend.domain.member.service.EmailService;
import com.project.everytimeclonecodingbackend.domain.member.service.MemberService;
import com.project.everytimeclonecodingbackend.global.security.JwtTokenProvider;
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
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
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
    @Autowired
    EmailService emailService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    Logger log = LoggerFactory.getLogger(MemberControllerTest.class);

    private Member member;
    private String accessToken;

    @BeforeEach
    void setup() {
        memberService.deleteAll();
    }

    @Test
    @DisplayName("?????? ????????? ???????????? id??? ???????????????.")
    void signupTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MemberSignupRequestDto memberSignupRequestDto = new MemberSignupRequestDto(
                "cloudwi",
                "testPassword",
                "????????????",
                "?????????",
                "???????????????",
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
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("school").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("admissionId").type(JsonFieldType.NUMBER).description("??????")
                                ),
                                responseFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("?????????")
                                )
                        )
                );
    }

    @Test
    @DisplayName("?????? ????????? ??? ????????? ?????? ?????????.")
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
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("????????????")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("??????")
                                )
                        )
                );
    }

    @Test
    @DisplayName("????????? ?????? api??? ?????? ???????????? ?????? ????????? ????????????.")
    void emailTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        signup();
        login();

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
                                        headerWithName("AccessToken").description("??????")
                                ),
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????????")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("????????????")
                                )
                        )
                );
    }

    @Test
    @DisplayName("????????? ?????? ????????? ?????? ??? ??? ??????.")
    void checkEmailTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        signup();
        login();

        String thisAccessToken = jwtTokenProvider.bearerRemove(accessToken);
        Authentication authentication = jwtTokenProvider.getAuthentication(thisAccessToken);

        String authNum = emailService.sendEmail("cloudwi@naver.com", authentication);

        MemberCheckEmailRequestDto memberCheckEmailRequestDto = new MemberCheckEmailRequestDto(authNum);

        mockMvc.perform(
                post("/api/v1/member/email/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .header("AccessToken", accessToken)
                        .content(objectMapper.writeValueAsString(memberCheckEmailRequestDto))
                )
                .andDo(
                        document(
                                "MemberController/email/check",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("AccessToken").description("??????")
                                ),
                                requestFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("?????? ??????")
                                ),
                                responseFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("?????????")
                                )
                        )
                );
    }


    private void signup() {
        MemberSignupRequestDto memberSignupRequestDto = new MemberSignupRequestDto(
                "cloudwi",
                "testPassword",
                "????????????",
                "?????????",
                "???????????????",
                17
        );
        member = memberService.signup(
                memberSignupRequestDto.getuserId(),
                memberSignupRequestDto.getPassword(),
                memberSignupRequestDto.getNickname(),
                memberSignupRequestDto.getName(),
                memberSignupRequestDto.getSchool(),
                memberSignupRequestDto.getAdmissionId()
        );

        log.info("???????????? ??????");
    }

    private void login() {

        MemberLoginRequestDto memberLoginRequestDto = new MemberLoginRequestDto(
                "cloudwi",
                "testPassword"
        );
        accessToken = memberService.login(
                memberLoginRequestDto.getUserId(),
                memberLoginRequestDto.getPassword()
        );

        log.info("????????? ??????");
    }
}