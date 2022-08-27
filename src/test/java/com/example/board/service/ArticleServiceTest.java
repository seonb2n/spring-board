package com.example.board.service;

import com.example.board.domain.Article;
import com.example.board.domain.UserAccount;
import com.example.board.domain.type.SearchType;
import com.example.board.dto.ArticleDto;
import com.example.board.dto.ArticleWithCommentsDto;
import com.example.board.dto.UserAccountDto;
import com.example.board.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService sut;

    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("검색어 없이 게시글을 검색하면, 게시글 페이지를 반환한다.")
    @Test
    public void givenNoSearchParameters_whenSearchingArticles_thenReturnsArticlePage() throws Exception {
        //given
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findAll(pageable)).willReturn(Page.empty());

        //when
        Page<ArticleDto> articleDtos = sut.searchArticles(null, null, pageable);

        //then
        assertThat(articleDtos).isEmpty();
        then(articleRepository).should().findAll(pageable);
    }

    @DisplayName("검색어와 함께 게시글을 검색하면, 게시글 페이지를 반환한다")
    @Test
    public void givenSearchParameters_whenSearchingArticles_thenReturnsArticlePage() throws Exception {
        //given
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitleContaining(searchKeyword, pageable)).willReturn(Page.empty());

        //when
        Page<ArticleDto> articleDtos = sut.searchArticles(searchType, searchKeyword, pageable);

        //then
        assertThat(articleDtos).isEmpty();
        then(articleRepository).should().findByTitleContaining(searchKeyword, pageable);
    }


    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    public void givenArticleId_whenSearchingArticle_thenReturnsArticle() throws Exception {
        //given
        Long articleId = 1L;
        Article article = createArticle();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        //when
        ArticleWithCommentsDto dto = sut.getArticle(articleId);

        //then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("hashtag", article.getHashtag());
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("없는 게시글을 조회하면 예외를 던진다")
    @Test
    public void givenNonexistentArticleId_whenSearchingArticle_thenThrowsException() throws Exception {
        //given
        Long articleId = 0L;
        given(articleRepository.findById(articleId)).willReturn(Optional.empty());

        //when
        Throwable t = catchThrowable(() -> sut.getArticle(articleId));

        //then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("게시글이 없습니다 - articleId: " + articleId);
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void givenArticle_whenSavingArticle_thenSavesArticle() throws Exception {
        //given
        ArticleDto dto = createArticleDto();
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
        Article article = createArticle();
        ArticleDto dto = createArticleDto("새 타이틀", "새 내용", "#springboot");
        given(articleRepository.save(any(Article.class))).willReturn(null);

        //when
        sut.updateArticle(dto);

        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("없는 게시글의 수정 정보를 입력하면 경고 로그를 직는다.")
    @Test
    public void givenNonexistentArticleInfo_whenUpdatingArticle_thenLogsWarningAndDoesNothing() throws Exception {
        //given
        ArticleDto dto = createArticleDto("새 타이틀", "새 내용", "#springboot");
        given(articleRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

        //when
        sut.updateArticle(dto);

        //then
        then(articleRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("게시글 ID를 입력하면, 게시글을 삭제한다.")
    @Test
    void givenArticleId_whenDeleteArticle_thenDeleteArticle() {
        //given
        willDoNothing().given(articleRepository).delete(any(Article.class));

        //when
        sut.deleteArticle(1L);

        //then
        then(articleRepository).should().delete(any(Article.class));
    }

    @DisplayName("게시글 수를 조회하면, 게시글 수를 반환한다.")
    @Test
    public void givenNothing_whenCountingArticles_thenReturnsArticleCount() throws Exception {
        //given
        long expected = 0L;
        given(articleRepository.count()).willReturn(expected);

        //when
        long actual = sut.getArticleCount();

        //then
        assertThat(actual).isEqualTo(expected);
        then(articleRepository).should().count();
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "uno",
                "password",
                "uno@email.com",
                "Uno",
                null
        );
    }

    private Article createArticle() {
        return Article.of(
                createUserAccount(),
                "title",
                "content",
                "#java"
        );
    }

    private ArticleDto createArticleDto() {
        return createArticleDto("title", "content", "#java");
    }

    private ArticleDto createArticleDto(String title, String content, String hashtag) {
        return ArticleDto.of(1L,
                createUserAccountDto(),
                title,
                content,
                hashtag,
                LocalDateTime.now(),
                "Uno",
                LocalDateTime.now(),
                "Uno");
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                1L,
                "uno",
                "password",
                "uno@mail.com",
                "Uno",
                "This is memo",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }

}