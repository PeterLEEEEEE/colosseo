package com.colosseo.global.config.redis;

public class CacheKey {
    private CacheKey() {}
    public static final int DEFAULT_EXPIRATION_HOUR = 2;

    public static final int ARTICLE_EXPIRATION_HOUR = 1;
    public static final int USER_EXPIRATION_HOUR = 3;

    public static final String USER = "USER";
    public static final String ARTICLE = "ARTICLE";
}
