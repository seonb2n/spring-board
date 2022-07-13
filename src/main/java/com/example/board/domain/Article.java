package com.example.board.domain;

import javax.persistence.Id;
import java.time.LocalDateTime;

public class Article {
    @Id
    private Long id;
    private String title;
    private String content;
    private String hashtag;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifedAt;
    private String modifiedBy;
}
