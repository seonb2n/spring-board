package com.example.board.controller;

import com.example.board.config.SpringSecurityConfig;
import com.example.board.service.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SpringSecurityConfig.class)
@WebMvcTest(MainController.class)
class MainControllerTest {

    private final MockMvc mvc;

    @MockBean
    UserAccountService userAccountService;

    public MainControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    public void givenRootPath_whenRequestTootPage_thenRedirectsToArticlesPage() throws Exception {
        //given

        // When & Then
        mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());
    }
}