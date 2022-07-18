package com.example.board.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName(("Data Rest - API 테스트"))
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {

    private final MockMvc mvc;

    public DataRestTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[api] 게시글 리스트 조회")
    @Test
    public void api_articles_jsonResponse_test() throws Exception {
        //given


        //when & then
        mvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

    }

    @DisplayName("[api] 게시글 단건 조회")
    @Test
    public void api_article_jsonResponse_test() throws Exception {
        //given


        //when & then
        mvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

    }

    @DisplayName("[api] 게시글 댓글 리스트 조회")
    @Test
    public void api_articleComments_jsonResponse_test() throws Exception {
        //given


        //when & then
        mvc.perform(get("/api/articles/1/articleCommentSet"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

    }


    @DisplayName("[api] 댓글 리스트 조회")
    @Test
    public void api_comments_jsonResponse_test() throws Exception {
        //given


        //when & then
        mvc.perform(get("/api/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

    }


    @DisplayName("[api] 게시글 댓글 단건 조회")
    @Test
    public void api_comment_jsonResponse_test() throws Exception {
        //given

        //when & then
        mvc.perform(get("/api/articleComments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

    }

}
