package com.example.board.service;

import com.example.board.domain.Article;
import com.example.board.domain.type.SearchType;
import com.example.board.dto.ArticleDto;
import com.example.board.dto.ArticleUpdateDto;
import com.example.board.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService sut;

    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    public void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList() throws Exception {
        //given

        //when
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); //제목, 본문, id, 닉네임, 해시태그

        //then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글을 조회하면, 게시글 리스트를 반환한다.")
    @Test
    public void givenId_whenSearchingArticles_thenReturnsArticleList() throws Exception {
        //given

        //when
        ArticleDto articles = sut.searchArticle(1L); //제목, 본문, id, 닉네임, 해시태그

        //then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void givenArticle_whenSavingArticle_thenSavesArticle() throws Exception {
        //given
        ArticleDto dto = ArticleDto.of(LocalDateTime.now(), "sb", "title", "content", "hashtag");
        given(articleRepository.save(any(Article.class))).willReturn(null);

        //when
        sut.saveArticle(dto);

        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글 ID와 수정 정보를 입력하면, 게시글을 수정한다.")
    @Test
    void givenArticle_whenModifiedArticle_thenUpadateArticle() throws Exception {
        //given
        ArticleUpdateDto dto = ArticleUpdateDto.of("title", "content", "hashtag");
        given(articleRepository.save(any(Article.class))).willReturn(null);

        //when
        sut.updateArticle(1L, dto);

        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글 ID를 입력하면, 게시글을 삭제한다.")
    @Test
    void givenArticleId_whenDeleteArticle_thenDeleteArticle() throws Exception {
        //given
        willDoNothing().given(articleRepository).delete(any(Article.class));

        //when
        sut.deleteArticle(1L);

        //then
        then(articleRepository).should().delete(any(Article.class));
    }

}