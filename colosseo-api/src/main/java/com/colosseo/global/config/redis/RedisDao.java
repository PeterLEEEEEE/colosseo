package com.colosseo.global.config.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisDao {
    private final RedisTemplate<String, String> redisTemplate;
//    private final RedisTemplate<?, ?> redisCntTemplate;
    public void setValues(String key, String data) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data);
    }

    public void setValues(String key, String data, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    public void setValues(String accessToken, String logout, Long expiration, TimeUnit microseconds) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(accessToken, logout, expiration, microseconds);
    }

    public String getValues(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
//        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
        return values.get(key);
//        }
//        return "NONE";
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

//    public void setCountValues(String key, Integer value) {
//        ValueOperations<byte[], byte[]> values = redisCntTemplate.opsForValue();
//        values.set(key, String.valueOf(value));
//    }

    public void increaseValues(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        log.info("value:{}", values.get(key));
        values.increment(key);

        log.info("value:{}", values.get(key));
    }
}
