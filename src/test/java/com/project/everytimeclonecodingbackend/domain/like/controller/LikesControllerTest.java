package com.project.everytimeclonecodingbackend.domain.like.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.everytimeclonecodingbackend.domain.like.dto.LikeSaveRequestDto;
import com.project.everytimeclonecodingbackend.domain.member.entity.Member;
import com.project.everytimeclonecodingbackend.domain.member.service.EmailService;
import com.project.everytimeclonecodingbackend.domain.member.service.MemberService;
import com.project.everytimeclonecodingbackend.domain.post.entity.Post;
import com.project.everytimeclonecodingbackend.domain.post.service.PostService;
import com.project.everytimeclonecodingbackend.global.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class LikesControllerTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private PostService postService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private EmailService emailService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void save() throws Exception {
        Member member = new Member("cloudwi", "testPassword", "????????????", "?????????", "???????????????", 17);
        memberService.signup(member.getUserId(), member.getPassword(), member.getNickname(), member.getName(), member.getSchool().toString(), member.getAdmissionId());
        String accessToken = memberService.login(member.getUserId(), member.getPassword());

        String thisAccessToken = jwtTokenProvider.bearerRemove(accessToken);
        Authentication authentication = jwtTokenProvider.getAuthentication(thisAccessToken);

        String authCode = emailService.sendEmail("cloudwi@naver.com", authentication);
        memberService.checkEmail(authCode, authentication);

        Post post = postService.save("??????", "??????", "???????????????", true, authentication);

        LikeSaveRequestDto likeSaveRequestDto = new LikeSaveRequestDto(
                post.getId()
        );

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(
                post("/api/v1/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .header("AccessToken", accessToken)
                        .content(objectMapper.writeValueAsString(likeSaveRequestDto))
        )
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "LikeController/save",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("AccessToken").description("??????")
                                ),
                                requestFields(
                                        fieldWithPath("postId").type(JsonFieldType.NUMBER).description("????????? ?????????")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ?????????")
                                )
                        )
                );
    }
}