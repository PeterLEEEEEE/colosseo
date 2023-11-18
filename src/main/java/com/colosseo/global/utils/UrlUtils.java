package com.colosseo.global.utils;

import lombok.Getter;

import java.util.List;

@Getter
public class UrlUtils {

    public static final List<String> permittedUrl = List.of(
            "/",
            "/error",
            "/api-docs/**",
            "/v1/api-docs/**",
            "/swagger-ui/**",
            "/docs/**",
            "/oauth2/callback/**",
            "/oauth/**",
            "/favicon.ico",
            "/api/v1/login",
            "/api/v1/signup",
            "/api/v1/health-check"
    );
}
