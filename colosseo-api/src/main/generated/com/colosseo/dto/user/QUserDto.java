package com.colosseo.dto.user;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.colosseo.dto.user.QUserDto is a Querydsl Projection type for UserDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QUserDto extends ConstructorExpression<UserDto> {

    private static final long serialVersionUID = 740986664L;

    public QUserDto(com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<String> email, com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<com.colosseo.global.enums.AccountStatusType> accountStatusType, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt, com.querydsl.core.types.Expression<com.colosseo.global.enums.RoleType> roleType, com.querydsl.core.types.Expression<String> createdBy, com.querydsl.core.types.Expression<java.time.LocalDateTime> updatedAt, com.querydsl.core.types.Expression<String> modifiedBy) {
        super(UserDto.class, new Class<?>[]{long.class, String.class, String.class, com.colosseo.global.enums.AccountStatusType.class, java.time.LocalDateTime.class, com.colosseo.global.enums.RoleType.class, String.class, java.time.LocalDateTime.class, String.class}, userId, email, nickname, accountStatusType, createdAt, roleType, createdBy, updatedAt, modifiedBy);
    }

}

