package com.example.board.service;

import com.example.board.domain.Article;
import com.example.board.domain.ArticleComment;
import com.example.board.dto.ArticleCommentDto;
import com.example.board.repository.ArticleCommentRepository;
import com.example.board.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
public class ArticleCommentServiceTest {

    @InjectMocks
    private ArticleCommentService sut;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleCommentRepository articleCommentRepository;

    @DisplayName("게시글 Id 로 조회하면, 해당 댓글 리스트 반환")
    @Test
    public void givenArticleId_whenSearchingComments_thenReturnsComments() throws Exception {
        //given
        Long articleId = 1L;
        var article = Article.of("title", "content", "hashtag");
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        //when
        List<ArticleCommentDto> articleComments = sut.searchArticleComment();

        //then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("댓글 정보 입력하면, 해당 댓글 저장")
    @Test
    public void givenArticleDto_whenArticleDto_thenSaveArticle() throws Exception {
        //given
        var articleCommentDto = ArticleCommentDto.of(LocalDateTime.now(), "sb", LocalDateTime.now(), "sb", "content");

        //when
        sut.saveArticle(articleCommentDto);

        //then
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }
}
