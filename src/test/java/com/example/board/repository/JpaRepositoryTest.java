package com.example.board.repository;

import com.example.board.config.JpaConfig;
import com.example.board.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test
    public void given_whenSelecting_thenWorksFine() throws Exception {
        //given


        //when
        List<Article> articles = articleRepository.findAll();

        //then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }

    @DisplayName("insert 테스트")
    @Test
    public void given_whenInserting_thenWorksFine() throws Exception {
        //given
        long previousCount = articleRepository.count();
        Article article = Article.of("new article", "new content", "#spring");

        //when
        Article savedArticle = articleRepository.save(article);

        //then
        assertThat(articleRepository.count())
                .isNotNull()
                .isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    public void given_whenUpdating_thenWorksFine() throws Exception {
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        //when
        Article savedArticle = articleRepository.saveAndFlush(article);

        //then
        assertThat(savedArticle)
                .hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    public void given_whenDeleting_thenWorksFine() throws Exception {
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentSize = article.getArticleCommentSet().size();

        //when
        articleRepository.delete(article);

        //then
        assertThat(articleRepository.count())
                .isEqualTo(previousArticleCount-1);

        assertThat(articleCommentRepository.count())
                .isEqualTo(previousArticleCommentCount - deletedCommentSize);
    }
}