package com.colosseo.dto.article;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.colosseo.dto.article.QArticleDto is a Querydsl Projection type for ArticleDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QArticleDto extends ConstructorExpression<ArticleDto> {

    private static final long serialVersionUID = 1111604280L;

    public QArticleDto(com.querydsl.core.types.Expression<Long> articleId, com.querydsl.core.types.Expression<? extends com.colosseo.dto.user.UserDto> userDto, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<Long> likes, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt, com.querydsl.core.types.Expression<String> createdBy, com.querydsl.core.types.Expression<java.time.LocalDateTime> updatedAt, com.querydsl.core.types.Expression<String> modifiedBy) {
        super(ArticleDto.class, new Class<?>[]{long.class, com.colosseo.dto.user.UserDto.class, String.class, String.class, String.class, long.class, java.time.LocalDateTime.class, String.class, java.time.LocalDateTime.class, String.class}, articleId, userDto, title, content, nickname, likes, createdAt, createdBy, updatedAt, modifiedBy);
    }

}

