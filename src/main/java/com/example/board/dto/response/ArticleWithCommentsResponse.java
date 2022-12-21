package com.example.board.dto.response;

import com.example.board.dto.ArticleCommentDto;
import com.example.board.dto.ArticleWithCommentsDto;
import com.example.board.dto.HashtagDto;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public record ArticleWithCommentsResponse(
        Long id,
        String title,
        String content,
        Set<String> hashtags,
        LocalDateTime createdAt,
        String email,
        String nickname,
        String userId,
        Set<ArticleCommentResponse> articleCommentsResponse
) {

    public static ArticleWithCommentsResponse of(Long id, String title, String content, Set<String> hashtags, LocalDateTime createdAt, String email, String nickname, String userId, Set<ArticleCommentResponse> articleCommentResponses) {
        return new ArticleWithCommentsResponse(id, title, content, hashtags, createdAt, email, nickname, userId, articleCommentResponses);
    }

    public static ArticleWithCommentsResponse from(ArticleWithCommentsDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return new ArticleWithCommentsResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtagDtos().stream()
                        .map(HashtagDto::hashtagName)
                        .collect(Collectors.toUnmodifiableSet())
                ,
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname,
                dto.userAccountDto().userId(),
                //articleSet 을 대댓글 계층구조에 맞는 treeSet 형태로 변환하여 반환한다.
                organizeArticleComment(dto.articleCommentDtos())
        );
    }

    /**
     * ArticleCommentDto set 을 대댓글 관계에 맞는 형태의 TreeSet 으로 매핑
     * @param dtos
     * @return
     */
    private static Set<ArticleCommentResponse> organizeArticleComment(Set<ArticleCommentDto> dtos) {
        //ArticleCommentDto 를 Id 를 key 로 하는 ArticleCommentResponse Map 으로 만든다.
        Map<Long, ArticleCommentResponse> map = dtos.stream()
                .map(ArticleCommentResponse::from)
                .collect(Collectors.toMap(ArticleCommentResponse::id, Function.identity()));

        //map 을 순환하면서 대댓글을 추출해서 부모에 매핑한다.
        map.values().stream().filter(ArticleCommentResponse::hasParentComment)
                .forEach(comment -> {
                    var parentComment = map.get(comment.parentCommentId());
                    parentComment.childComments().add(comment);
                });

        //map 내부에서 부모가 없는 녀석들만 모아서 TreeSet 으로 반환한다.
        return map.values().stream().filter(comment -> !comment.hasParentComment())
                .collect(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator
                                .comparing(ArticleCommentResponse::createdAt)
                                .reversed()
                                .thenComparingLong(ArticleCommentResponse::id)
                        )));
    }

}

