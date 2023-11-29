package com.colosseo.dto.article;

import com.colosseo.model.article.Article;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleResponse {
    private Long articleId;
    private Long userId;
    private String title;
    private String content;
    private String author;
    private Integer views;
    private Integer likes;
    private LocalDateTime createdAt;

    @Builder
    @QueryProjection
    public ArticleResponse(Long articleId, Long userId, String title, String content, String author, Integer views, Integer likes, LocalDateTime createdAt) {
        this.articleId = articleId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.views = views;
        this.likes = likes;
        this.createdAt = createdAt;
    }

    public static ArticleResponse from(Article article) {
        return ArticleResponse.builder()
                .articleId(article.getId())
                .userId(article.getUserId())
                .title(article.getTitle())
                .content(article.getContent())
                .author(article.getUser().getNickname())
                .views(article.getViewCount())
                .likes(article.getLikeCount())
                .createdAt(article.getCreatedAt())
                .build();
    }
}
