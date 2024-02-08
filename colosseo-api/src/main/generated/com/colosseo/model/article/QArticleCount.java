package com.colosseo.model.article;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticleCount is a Querydsl query type for ArticleCount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticleCount extends EntityPathBase<ArticleCount> {

    private static final long serialVersionUID = -1280350178L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticleCount articleCount = new QArticleCount("articleCount");

    public final com.colosseo.model.QBaseTimeEntity _super = new com.colosseo.model.QBaseTimeEntity(this);

    public final QArticle article;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.colosseo.model.user.QUser user;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QArticleCount(String variable) {
        this(ArticleCount.class, forVariable(variable), INITS);
    }

    public QArticleCount(Path<? extends ArticleCount> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticleCount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticleCount(PathMetadata metadata, PathInits inits) {
        this(ArticleCount.class, metadata, inits);
    }

    public QArticleCount(Class<? extends ArticleCount> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new QArticle(forProperty("article"), inits.get("article")) : null;
        this.user = inits.isInitialized("user") ? new com.colosseo.model.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

