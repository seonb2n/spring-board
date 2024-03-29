package com.example.board.repository;

import com.example.board.domain.ArticleComment;
import com.example.board.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment>,
        QuerydslBinderCustomizer<QArticleComment> {

    /**
     * ArticleComment 와 연관관계가 있는 article 의 id 를 사용해서 ArticleComment 를 조회한다.
     *
     * @param articleId
     * @return
     */
    List<ArticleComment> findByArticle_Id(Long articleId);

    /**
     * ArticleComment 를 UserAccount 의 User Id 와 연관관계가 있는 경우에만 삭제한다.
     *
     * @param articleCommentId
     * @param userId
     */
    void deleteByIdAndUserAccount_UserId(Long articleCommentId, String userId);

    /**
     * querydsl 을 사용해서 나용을 포함하고 있는 comments 를 검색한다.
     *
     * @param bindings
     * @param root
     */
    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.content, root.createdAt, root.createdBy);
        //내용을 포함하고 있는 식으로 검색한다.
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }

}
