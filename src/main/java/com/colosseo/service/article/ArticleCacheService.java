package com.colosseo.service.article;

import com.colosseo.global.config.redis.RedisDao;
import com.colosseo.repository.ArticleDslRepository;
import com.colosseo.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleCacheService {
    private final RedisDao redisDao;
    private final ArticleRepository articleRepository;
    private final ArticleDslRepository articleDslRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public void increaseArticleViewCount(Long articleId) {

        String articleKey = "articleViewCnt::" + articleId;

        if (redisDao.getValues(articleKey) != null) {
            redisDao.increaseValues(articleKey);
            return;
        }

        redisDao.setValues(
                articleKey,
                String.valueOf(articleRepository.findViewCountById(articleId) + 1),
                Duration.ofMinutes(5)
        );
    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void deleteViewCntCacheFromRedis() {
        Set<String> redisKeys = redisTemplate.keys("articleViewCnt*");
        if (redisKeys != null) {
            for (String data : redisKeys) {
                Long articleId = Long.parseLong(data.split("::")[1]);
                Integer viewCnt = Integer.parseInt((String) redisTemplate.opsForValue().get(data));
                articleDslRepository.addViewCntFromRedis(articleId, viewCnt);
                redisTemplate.delete(data);
                redisTemplate.delete("ARTICLE::" + articleId);
            }
        }
    }
}
