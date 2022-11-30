package com.example.board.repository;

import com.example.board.domain.Article;
import com.example.board.domain.QArticle;
import com.example.board.repository.querydsl.ArticleRepositoryCustom;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ArticleRepository
        extends JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>,
        QuerydslBinderCustomizer<QArticle>,
        ArticleRepositoryCustom {

    /**
     * 제목을 포함해서 검색
     *
     * @param title
     * @param pageable
     * @return
     */
    Page<Article> findByTitleContaining(String title, Pageable pageable);

    /**
     * 내용을 포함해서 검색
     *
     * @param content
     * @param pageable
     * @return
     */
    Page<Article> findByContentContaining(String content, Pageable pageable);

    /**
     * 사용자 계정의 id 를 사용해서 검색
     *
     * @param userId
     * @param pageable
     * @return
     */
    Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pageable);

    /**
     * 사용자 계정의 닉네임을 사용해서 검색
     *
     * @param nickname
     * @param pageable
     * @return
     */
    Page<Article> findByUserAccount_NicknameContaining(String nickname, Pageable pageable);

    void deleteByIdAndUserAccount_UserId(Long articleId, String userId);

    /**
     * Querydsl 을 사용해서 내용을 포함하는 Article 을 검색한다.
     *
     * @param bindings
     * @param root
     */
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title, root.content, root.hashtags, root.createdAt, root.createdBy);
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtags.any().hashtagName).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }

    List<String> findAllDistinctHashtags();
}
