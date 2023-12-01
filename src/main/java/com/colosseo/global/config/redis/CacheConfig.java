package com.colosseo.global.config.redis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;

@EnableCaching
@Configuration

public class CacheConfig {

    /**
     * Spring Boot 가 기본적으로 RedisCacheManager 를 자동 설정해줘서 RedisCacheConfiguration 없어도 사용 가능
     * Bean 을 새로 선언하면 직접 설정한 RedisCacheConfiguration 이 적용됨
     */

//    @Bean
//    public ObjectMapper customObjectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
////        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
////        mapper.registerModule(new JavaTimeModule());
////        mapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
////        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
////        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
//        objectMapper.registerModule(new JavaTimeModule());
////        objectMapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
////        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
//
//        return objectMapper;
//    }
//    private GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer() {
//        ObjectMapper objectMapper = customObjectMapper();
////        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
////        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//
//        return new GenericJackson2JsonRedisSerializer(objectMapper);
//    }

    @Bean
    public GenericJackson2JsonRedisSerializer jsonRedisSerializer(
//            @Value("${spring.redis.serialization.class-property-type-name}") String classPropertyTypeName
    ) {
        GenericJackson2JsonRedisSerializer jsonRedisSerializer =
//                new GenericJackson2JsonRedisSerializer(classPropertyTypeName);
                new GenericJackson2JsonRedisSerializer();

        jsonRedisSerializer.configure(objectMapper -> {
            // add ObjectMapper customizations here
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//            objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        });

        return jsonRedisSerializer;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // LocalDateTime 직렬화
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
//        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);

        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofHours(CacheKey.DEFAULT_EXPIRATION_HOUR))
                .computePrefixWith(CacheKeyPrefix.simple()) // 임시
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper))
                        RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer())
                );

        HashMap<String, RedisCacheConfiguration> cacheConfigMap = new HashMap<>();
        cacheConfigMap.put(CacheKey.ARTICLE, RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofHours(CacheKey.ARTICLE_EXPIRATION_HOUR))
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper))
                        RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer())
                )
        );
        cacheConfigMap.put(CacheKey.USER, RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofHours(CacheKey.USER_EXPIRATION_HOUR))
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper))
                        RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer())
                )
        );

        return RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigMap)
                .build();
    }

    /**
     * 여러 Redis Cache 에 관한 설정을 하고 싶다면 RedisCacheManagerBuilderCustomizer 를 사용할 수 있음
     */
//    @Bean
//    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
//        return (builder) -> builder
//                .withCacheConfiguration("cache1",
//                        RedisCacheConfiguration.defaultCacheConfig()
//                                .computePrefixWith(cacheName -> "prefix::" + cacheName + "::")
//                                .entryTtl(Duration.ofSeconds(120))
//                                .disableCachingNullValues()
//                                .serializeKeysWith(
//                                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
//                                )
//                                .serializeValuesWith(
//                                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
//                                ))
//                .withCacheConfiguration("cache2",
//                        RedisCacheConfiguration.defaultCacheConfig()
//                                .entryTtl(Duration.ofHours(2))
//                                .disableCachingNullValues());
//    }
}
