package com.example.board.service;

import com.example.board.dto.ArticleCommentDto;
import com.example.board.repository.ArticleCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComment() {
        return List.of();
    }

    public void saveArticle(ArticleCommentDto articleCommentDto) {

    }
}
