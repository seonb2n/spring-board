package com.example.board.controller;

import com.example.board.config.TestSecurityConfig;
import com.example.board.service.ArticleCommentService;
import com.example.board.service.ArticleService;
import com.example.board.service.PaginationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(TestSecurityConfig.class)
@DisplayName("View 컨트롤러 - 인증")
public class AuthControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private ArticleCommentService articleCommentService;

    @MockBean
    private PaginationService paginationService;

    public AuthControllerTest(@Autowired MockMvc mvc) {
        this.mockMvc = mvc;
    }

    @Test
    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상 호출")
    public void givenNothing_whenTryingToLogIn_thenReturnLoginView() throws Exception {
        //given

        //when & then
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @DisplayName("[view] [GET] 로그인 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenTryingToLogIn_thenReturnsLogInView() throws Exception {
        //given


        //when & then
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));

    }


}
