package com.example.board.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
@DisplayName("View 컨트롤러 - 게시글")
class ArticleControllerTest {

    private final MockMvc mockMvc;
    
    public ArticleControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상 호출")
    public void givenNothing_whenRequestingArticlesView_thenReturnArticlesView() throws Exception {
        //given

        //when & then
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"));
    }

    @Test
    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상 호출")
    public void givenNothing_whenRequestingArticleDetailView_thenReturnArticlesView() throws Exception {
        //given

        //when & then
        mockMvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"));
    }


    @Disabled("구현 중")
    @Test
    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상 호출")
    public void givenNothing_whenRequestingArticlesSearchView_thenReturnArticleSearchView() throws Exception {
        //given

        //when & then
        mockMvc.perform(get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search"));
    }

    @Disabled("구현 중")
    @Test
    @DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출")
    public void givenNothing_whenRequestingArticleHashtagSearchView_thenReturnArticleHashtagSearchView() throws Exception {
        //given

        //when & then
        mockMvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search-hashtag"));
    }
}