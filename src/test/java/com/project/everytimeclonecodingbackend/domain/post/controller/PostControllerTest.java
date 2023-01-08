package com.project.everytimeclonecodingbackend.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.everytimeclonecodingbackend.domain.comment.service.CommentService;
import com.project.everytimeclonecodingbackend.domain.member.entity.Member;
import com.project.everytimeclonecodingbackend.domain.member.repository.MemberRepository;
import com.project.everytimeclonecodingbackend.domain.member.service.EmailService;
import com.project.everytimeclonecodingbackend.domain.member.service.MemberService;
import com.project.everytimeclonecodingbackend.domain.post.dto.PostDeleteRequestDto;
import com.project.everytimeclonecodingbackend.domain.post.dto.PostSaveRequestDto;
import com.project.everytimeclonecodingbackend.domain.post.entity.Post;
import com.project.everytimeclonecodingbackend.domain.post.repository.PostRepository;
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

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
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
    private PostRepository postRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private EmailService emailService;
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

        String thisAccessToken = jwtTokenProvider.bearerRemove(accessToken);
        Authentication authentication = jwtTokenProvider.getAuthentication(thisAccessToken);

        String authCode = emailService.sendEmail("cloudwi@naver.com", authentication);
        memberService.checkEmail(authCode, authentication);

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
                                        fieldWithPath("postId").type(JsonFieldType.NUMBER).description("아이디"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("작성자"),
                                        fieldWithPath("createTime").type(JsonFieldType.STRING).description("작성 시간")
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

        String authCode = emailService.sendEmail("cloudwi@naver.com", authentication);
        memberService.checkEmail(authCode, authentication);

        Post post = postService.save("제목", "내용", "비밀게시판", true, authentication);

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

    @Test
    @DisplayName("게시글 아이디로 게시글을 상세 조회할 수 있습니다.")
    void findById() throws Exception {
        memberService.signup("cloudwi", "testPassword", "동글구름", "장주영", "동의대학교", 17);

        String accessToken = memberService.login("cloudwi", "testPassword");
        String thisAccessToken = jwtTokenProvider.bearerRemove(accessToken);
        Authentication authentication = jwtTokenProvider.getAuthentication(thisAccessToken);

        String authCode = emailService.sendEmail("cloudwi@naver.com", authentication);
        memberService.checkEmail(authCode, authentication);

        Post post = postService.save("제목", "내용", "비밀게시판", true, authentication);

        mockMvc.perform(
                        get("/api/v1/post/{category}/{id}", post.getCategory().toString(), post.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .header("AccessToken", accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(post.getId()))
                .andDo(
                        document(
                                "PostController/findById",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("category").description("게시판 카테고리"),
                                        parameterWithName("id").description("게시판 아이디")
                                ),
                                requestHeaders(
                                        headerWithName("AccessToken").description("토큰")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("createTime").type(JsonFieldType.STRING).description("게시물 생성 시간"),
                                        fieldWithPath("commentFindAllDtos[]").type(JsonFieldType.ARRAY).description("댓글 들"),
                                        fieldWithPath("commentFindAllDtos[].id").type(JsonFieldType.NUMBER).description("댓글 아이디").optional(),
                                        fieldWithPath("commentFindAllDtos[].content").type(JsonFieldType.STRING).description("댓글 내용").optional(),
                                        fieldWithPath("commentFindAllDtos[].nickname").type(JsonFieldType.STRING).description("댓글 작성자").optional(),
                                        fieldWithPath("commentFindAllDtos[].createTime").type(JsonFieldType.STRING).description("댓글 작성 시간").optional(),
                                        fieldWithPath("deletable").type(JsonFieldType.BOOLEAN).description("삭제 여부"),
                                        fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("공감 수")
                                )
                        )
                );
    }

    @Test
    @DisplayName("게시글을 삭제가 정상 작동하면 200코드가 반환됩니다.")
    void deleteById() throws Exception {
        memberService.signup("cloudwi", "testPassword", "동글구름", "장주영", "동의대학교", 17);

        String accessToken = memberService.login("cloudwi", "testPassword");
        String thisAccessToken = jwtTokenProvider.bearerRemove(accessToken);
        Authentication authentication = jwtTokenProvider.getAuthentication(thisAccessToken);

        String authCode = emailService.sendEmail("cloudwi@naver.com", authentication);
        memberService.checkEmail(authCode, authentication);

        Post post = postService.save("제목", "내용", "비밀게시판", true, authentication);

        PostDeleteRequestDto postDeleteRequestDto = new PostDeleteRequestDto(post.getId());

        mockMvc.perform(
                        delete("/api/v1/post")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .header("AccessToken", accessToken)
                                .content(objectMapper.writeValueAsString(postDeleteRequestDto))
                )
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "PostController/deleteById",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("AccessToken").description("토큰")
                                ),
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 아이디")
                                )
                        )
                );
    }
}