//package com.colosseo.repository;
//
//import com.colosseo.model.article.Article;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.time.Duration;
//import java.util.Optional;
//
//@Slf4j
//@Repository
//@RequiredArgsConstructor
//public class ArticleCacheRepository {
//
//    private final RedisTemplate<String, Article> articleRedisTemplate;
//    private final static Duration EXPIRATION_TIME = Duration.ofHours(1);
//    public String initKey(Long articleId) {
//        return "ARTICLE:" + articleId;
//    }
//
//    public void setArticle(Article article) {
//        String key = initKey(article.getId());
//        log.info("Set Article to Redis Cache {}: {}", key, article);
//        articleRedisTemplate.opsForValue().set(key, article, EXPIRATION_TIME);
//    }
//
//    public Optional<Article> getArticle(final String key) {
//
//        return Optional.ofNullable(articleRedisTemplate.opsForValue().get(key));
//    }
//}
