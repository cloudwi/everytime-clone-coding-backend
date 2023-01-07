package com.project.everytimeclonecodingbackend.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.everytimeclonecodingbackend.domain.member.entity.Member;
import com.project.everytimeclonecodingbackend.domain.member.repository.MemberRepository;
import com.project.everytimeclonecodingbackend.domain.member.service.MemberService;
import com.project.everytimeclonecodingbackend.domain.post.dto.PostSaveRequestDto;
import com.project.everytimeclonecodingbackend.domain.post.entity.Category;
import com.project.everytimeclonecodingbackend.domain.post.entity.Post;
import com.project.everytimeclonecodingbackend.domain.post.repository.PostRepository;
import com.project.everytimeclonecodingbackend.domain.post.service.PostService;
import com.project.everytimeclonecodingbackend.global.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeAll;
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

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PostService postService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        memberRepository.deleteAll();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("게시물을 작성하면 게시물 아이디가 나옵니다.")
    void save() throws Exception {
        Member member = new Member("cloudwi", "testPassword", "동글구름", "장주영", "동의대학교", 17);
        memberService.signup(member.getUserId(), member.getPassword(), member.getNickname(), member.getName(), member.getSchool().toString(), member.getAdmissionId());
        String accessToken = memberService.login(member.getUserId(), member.getPassword());

        PostSaveRequestDto postSaveRequestDto = new PostSaveRequestDto(
                "제목임",
                "내용임",
                "비밀게시판",
                true
        );

        mockMvc.perform(
                        post("/api/v1/post")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .header("AccessToken", accessToken)
                                .content(objectMapper.writeValueAsString(postSaveRequestDto))
                )
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "PostController/save",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("AccessToken").description("토큰")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("category").type(JsonFieldType.STRING).description("카테고리"),
                                        fieldWithPath("anonymous").type(JsonFieldType.BOOLEAN).description("익명 여부")
                                ),
                                responseFields(
                                        fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 아이디")
                                )
                        )
                );
    }

    @Test
    @DisplayName("게시물은 카테고리 별로 조회가 가능하다.")
    void findAllByCategory() throws Exception {
        Member member = new Member("cloudwi", "testPassword", "동글구름", "장주영", "동의대학교", 17);
        memberService.signup(member.getUserId(), member.getPassword(), member.getNickname(), member.getName(), member.getSchool().toString(), member.getAdmissionId());
        String accessToken = memberService.login(member.getUserId(), member.getPassword());

        String thisAccessToken = jwtTokenProvider.bearerRemove(accessToken);
        Authentication authentication = jwtTokenProvider.getAuthentication(thisAccessToken);

        Post post = postService.save("제목", "내용", "비밀게시판", true, authentication);

        postService.save(
                post.getTitle(),
                post.getContent(),
                post.getCategory().toString(),
                post.isAnonymous(),
                authentication
        );
        mockMvc.perform(
                        get("/api/v1/post/{category}", post.getCategory().toString())
                                .param("page", String.valueOf(0))
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .header("AccessToken", accessToken)
                )
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "PostController/findAllByCategory",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("category").description("게시판 카테고리")
                                ),
                                queryParameters(
                                        parameterWithName("page").description("페이지")
                                ),
                                requestHeaders(
                                        headerWithName("AccessToken").description("토큰")
                                ),
                                responseFields(
                                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("아이디"),
                                        fieldWithPath("[].title").type(JsonFieldType.STRING).description("게시글들"),
                                        fieldWithPath("[].content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("[].nickname").type(JsonFieldType.STRING).description("작성자"),
                                        fieldWithPath("[].createTime").type(JsonFieldType.STRING).description("생성 시간")
                                )
                        )
                );
    }
//
//    @Test
//    void search() {
//    }
}