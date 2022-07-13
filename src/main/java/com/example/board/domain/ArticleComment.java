package com.example.board.domain;

import javax.persistence.Id;
import java.time.LocalDateTime;

public class ArticleComment {
    @Id
    private Long id;

    private Article article;
    private String content;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifedAt;
    private String modifiedBy;
}
