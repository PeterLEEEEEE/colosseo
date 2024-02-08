package com.colosseo.model.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 163138049L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.colosseo.model.QBaseEntity _super = new com.colosseo.model.QBaseEntity(this);

    public final EnumPath<com.colosseo.global.enums.AccountStatusType> accountStatusType = createEnum("accountStatusType", com.colosseo.global.enums.AccountStatusType.class);

    public final QAuth auth;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isVerified = createBoolean("isVerified");

    public final DateTimePath<java.time.LocalDateTime> loginAt = createDateTime("loginAt", java.time.LocalDateTime.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final EnumPath<com.colosseo.global.enums.ProviderType> providerType = createEnum("providerType", com.colosseo.global.enums.ProviderType.class);

    public final EnumPath<com.colosseo.global.enums.RoleType> roleType = createEnum("roleType", com.colosseo.global.enums.RoleType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auth = inits.isInitialized("auth") ? new QAuth(forProperty("auth"), inits.get("auth")) : null;
    }

}

