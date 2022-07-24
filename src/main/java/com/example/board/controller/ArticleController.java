package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    @GetMapping
    public String articles(ModelMap map) {
        map.addAttribute("articles", List.of());

        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map) {
        //TODO articleDto 를 반환시켜줘야 함.
        map.addAttribute("article", "article");
        map.addAttribute("articleComments", List.of());

        return "articles/detail";
    }

}
