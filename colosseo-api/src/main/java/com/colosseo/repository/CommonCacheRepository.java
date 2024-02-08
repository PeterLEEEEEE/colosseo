//package com.colosseo.repository;
//
//
//import com.colosseo.model.article.Article;
//import lombok.RequiredArgsConstructor;
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
//public class CommonCacheRepository {
//    private final RedisTemplate<String, Object> commonRedisTemplate;
//    private final static Duration EXPIRATION_TIME = Duration.ofHours(1);
//
//    public String initKey(String prefix, Long id) {
//        return prefix + id;
//    }
//
//    public void setArticle(Object entity) {
//        String key = initKey(entity.getId());
//        log.info("Set Article to Redis Cache {}: {}", key, entity);
//        commonRedisTemplate.opsForValue().set(key, entity, EXPIRATION_TIME);
//    }
//
//    public Optional<Object> getArticle(final String key) {
//
//        return Optional.ofNullable(commonRedisTemplate.opsForValue().get(key));
//    }
//}
