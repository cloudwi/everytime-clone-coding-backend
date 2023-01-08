package com.project.everytimeclonecodingbackend.domain.comment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.everytimeclonecodingbackend.domain.comment.dto.CommentSaveRequestDto;
import com.project.everytimeclonecodingbackend.domain.member.entity.Member;
import com.project.everytimeclonecodingbackend.domain.member.repository.MemberRepository;
import com.project.everytimeclonecodingbackend.domain.member.service.MemberService;

import com.project.everytimeclonecodingbackend.domain.post.entity.Post;
import com.project.everytimeclonecodingbackend.domain.post.service.PostService;
import com.project.everytimeclonecodingbackend.global.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private MemberService memberService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PostService postService;
    @Autowired
    private MemberRepository memberRepository;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        memberRepository.deleteAll();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("댓글을 작성이 성공하면 Http 200 상태코드가 반환된다 ")
    void save() throws Exception {
        Member member = new Member("cloudwi", "testPassword", "동글구름", "장주영", "동의대학교", 17);
        memberService.signup(member.getUserId(), member.getPassword(), member.getNickname(), member.getName(), member.getSchool().toString(), member.getAdmissionId());
        String accessToken = memberService.login(member.getUserId(), member.getPassword());

        String thisAccessToken = jwtTokenProvider.bearerRemove(accessToken);
        Authentication authentication = jwtTokenProvider.getAuthentication(thisAccessToken);

        Post post = postService.save("제목", "내용", "비밀게시판", true, authentication);

        CommentSaveRequestDto commentSaveRequestDto = new CommentSaveRequestDto(
                "댓글 내용",
                1,
                true
        );

        mockMvc.perform(
                        post("/api/v1/comment")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .header("AccessToken", accessToken)
                                .content(objectMapper.writeValueAsString(commentSaveRequestDto))
                )
                .andExpect(status().isOk())
                .andDo(
                    document(
                            "CommentController/save",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            requestHeaders(
                                    headerWithName("AccessToken").description("토큰")
                            ),
                            requestFields(
                                    fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용"),
                                    fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                    fieldWithPath("anonymous").type(JsonFieldType.BOOLEAN).description("익명 여부")
                            ),
                            responseFields(
                                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                                    fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용"),
                                    fieldWithPath("postId").type(JsonFieldType.NUMBER).description("댓글이 작성된 게시글 아이디"),
                                    fieldWithPath("createTime").type(JsonFieldType.STRING).description("댓글 작성 시간"),
                                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("댓글 작성자 닉네임")
                            )
                    )
                );

    }
}