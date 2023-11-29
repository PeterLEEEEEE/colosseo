package com.colosseo.repository;

import com.colosseo.dto.article.ArticleDto;
import com.colosseo.dto.article.ArticleResponse;
import com.colosseo.dto.article.QArticleResponse;
import com.colosseo.global.enums.SearchType;
import com.colosseo.model.article.Article;
import com.colosseo.model.article.QArticle;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.colosseo.model.article.QArticle.article;

import static com.colosseo.model.user.QUser.user;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class ArticleDslRepository {
    private final JPAQueryFactory queryFactory;

    private BooleanExpression titleEq(final String title) {
        return hasText(title) ? article.title.eq(title) : null;
    }

    private BooleanExpression contentEq(final String content) {
        return content == null ? null : article.content.eq(content);
    }

    private BooleanExpression nicknameEq(final String nickname) {
        return nickname == null ? null : article.user.nickname.eq(nickname);
    }

    public Page<ArticleResponse> findArticleByCondition(SearchType searchType, final String keyword, Pageable pageable) {

        BooleanExpression search = null;
        switch (searchType) {
            case TITLE -> search = titleEq(keyword);
            case CONTENT -> search = contentEq(keyword);
            case NICKNAME -> search = nicknameEq(keyword);
        }

        List<ArticleResponse> result = queryFactory.select(
                    new QArticleResponse(
                        article.id.as("articleId"),
                        article.user.id.as("userId"),
                        article.title,
                        article.content,
                        article.user.nickname.as("author"),
                        article.viewCount.as("views"),
                        article.likeCount.as("likes"),
                        article.createdAt
                    )
                )
                .from(article)
//                .leftJoin(article, articleLike.article)
                .where(search)
                .orderBy(article.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = queryFactory.select(article)
                .from(article)
                .where(search)
                .stream().count();


        return new PageImpl<>(result, pageable, count);

//        queryFactory.select(new QArticleDto())
//                .from(article)
//                .where(
//                        titleEq(title)
//                )
//                .fetch();
//        return queryFactory.selectFrom(article)
//                .where(article.title.eq(title))
//                .fetch();
    }

//    public List<ArticleDto> findArticleByContent(final String content) {
//        return queryFactory.select(new QArticleDto(
//                    article.id.as("articleId"),
//                    article.title,
//                    new QUserDto(),
//                    article.content,
//                    article.likeCount.as("likes"),
//                    article.createdBy,
//                    article.createdAt
//                ))
//                .from(article)
//                .leftJoin(user)
//                .where(
//                    contentEq(content)
//                )
//                .fetch();
//    }

//    public List<ArticleDto> findArticleByNickname(final String nickname) {
//        return queryFactory.select(new QArticleDto())
//                .from(article)
//                .where(
//                        nicknameEq(nickname)
//                )
//                .fetch();
//    }
}
