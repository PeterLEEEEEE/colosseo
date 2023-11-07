package com.colosseo.validation.user;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordPatternValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordPattern {
    String message() default "비밀번호는 대문자 혹은 소문자, 숫자, 특수문자를 포함한 12자리 이상의 조합이어야 합니다";
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
}
