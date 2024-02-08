package com.colosseo.validation.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.regex.*;

@Component
@RequiredArgsConstructor
public class PasswordPatternValidator implements ConstraintValidator<PasswordPattern, String> {

    @Override
    public void initialize(PasswordPattern constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        String regex = "^(?=.*[0-9])"
//                       + "(?=.*[a-z])(?=.*[A-Z])"
                       + "(?=.*[a-zA-Z])"
                       + "(?=.*[@#$%!^&-+=()])"
                       + "(?=\\S+$).{12,20}$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);

        return m.matches();
    }
}
