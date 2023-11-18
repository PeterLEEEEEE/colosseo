package com.colosseo.repository;

import com.colosseo.dto.article.ArticleDto;
import com.colosseo.model.article.Article;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.colosseo.model.article.QArticle.article;

@Repository
@RequiredArgsConstructor
public class ArticleDslRepository {
    private final JPAQueryFactory queryFactory;

    private Predicate titleEq(final String title) {
        if (title == null) {
            return null;

        }
        return article.title.eq(title);
    }

    private Predicate contentEq(final String content) {
        return article.content.eq(content);
    }

    private Predicate nicknameEq(final String nickname) {
        return null;
//        return article.user.nickname.eq(nickname);
    }

    public List<ArticleDto> findArticleByCondition(final String condition) {
//        return queryFactory.select(Projections.constructor(
//                    ArticleDto.class,
//                    article.title,
//                    article.content))
//                .from(article)
//                .where(article.)
//                .fetch();
        queryFactory.selectFrom(article)
                .where(titleEq(condition), contentEq(), nicknameEq())
                .fetch();
//        return queryFactory.selectFrom(article)
//                .where(article.title.eq(title))
//                .fetch();
    }
}
