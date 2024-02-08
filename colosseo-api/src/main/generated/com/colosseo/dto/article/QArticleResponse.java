package com.colosseo.dto.article;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.colosseo.dto.article.QArticleResponse is a Querydsl Projection type for ArticleResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QArticleResponse extends ConstructorExpression<ArticleResponse> {

    private static final long serialVersionUID = -821564024L;

    public QArticleResponse(com.querydsl.core.types.Expression<Long> articleId, com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<String> author, com.querydsl.core.types.Expression<Integer> views, com.querydsl.core.types.Expression<Integer> likes, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt) {
        super(ArticleResponse.class, new Class<?>[]{long.class, long.class, String.class, String.class, String.class, int.class, int.class, java.time.LocalDateTime.class}, articleId, userId, title, content, author, views, likes, createdAt);
    }

}

