package com.colosseo.controller;

import com.colosseo.global.config.security.CustomPermissionEvaluator;
import com.colosseo.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class MethodSecurityConfig {

    private final ArticleRepository articleRepository;
    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
         var handler = new DefaultMethodSecurityExpressionHandler();
         handler.setPermissionEvaluator(new CustomPermissionEvaluator(articleRepository) );

         return handler;
    }
}
