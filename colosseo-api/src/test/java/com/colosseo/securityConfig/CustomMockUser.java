package com.colosseo.securityConfig;

import com.colosseo.global.enums.RoleType;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory =  MockUserFactory.class)
public @interface CustomMockUser {

    String username() default "test99@gmail.com";
    String password() default "abcdef12345!";
    RoleType roleType() default RoleType.ADMIN;
}
