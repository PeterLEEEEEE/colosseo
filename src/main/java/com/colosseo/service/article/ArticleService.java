package com.colosseo.service.article;

import com.colosseo.dto.article.ArticleDto;
import com.colosseo.dto.article.ArticleRequest;
import com.colosseo.dto.article.ArticleResponse;
import com.colosseo.dto.user.UserDto;
import com.colosseo.exception.CustomException;
import com.colosseo.exception.ErrorCode;
import com.colosseo.global.config.redis.CacheKey;
import com.colosseo.global.config.redis.RedisDao;
import com.colosseo.model.article.Article;
import com.colosseo.global.enums.SearchType;
import com.colosseo.model.article.ArticleCount;
import com.colosseo.model.article.ArticleLikeRepository;

import com.colosseo.repository.ArticleDslRepository;
import com.colosseo.repository.ArticleRepository;
import com.colosseo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import static io.micrometer.common.util.StringUtils.isBlank;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleDslRepository articleDslRepository;
    private final ArticleLikeRepository articleLikeRepository;
    private final RedisDao redisDao;

    public String postArticle(ArticleDto articleDto) {

        articleRepository.save(articleDto.toEntity());

        return "success";
    }

//    public void getArticle(Long articleId) {
//        articleRepository.findById(articleId)
//                .map(ArticleWithCommentsDto::from)
//    }

    public void increaseViewCount() {

    }
    @Transactional
    @Cacheable(cacheNames = CacheKey.ARTICLE, key = "#articleId", cacheManager = "cacheManager")
    public ArticleDto getArticleDetailWithComments(Long articleId, UserDto userDto) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new CustomException(ErrorCode.ARTICLE_NOT_EXISTS)
        );
        String viewedUser = redisDao.getValues("viewedUser");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23, 59, 59);

        if (viewedUser == null) {
            redisDao.setValues("viewedUser", userDto.getUserId().toString(), Duration.between(now, targetTime));
            article.increaseViewCount();
        }

        return article.toDto();
    }

    public String deleteArticle(Long userId, Long articleId) {
        Article article = articleRepository.getReferenceById(articleId);

//        articleRepository.deleteByIdAndUser_UserId(articleId, userId);
//        articleRepository.deleteById(articleId);
        if (!Objects.equals(article.getUser().getId(), userId)) {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }

        articleRepository.deleteById(articleId);

        return "successfully deleted";
    }

    // Spring data JPA로 페이징
    @Transactional(readOnly = true)

    public Page<ArticleResponse> getArticles(Pageable pageable, SearchType searchType, String searchKeyword) {
        if (searchType == null || isBlank(searchKeyword)) {
            return articleRepository.findAll(pageable)
                    .map(ArticleResponse::from);
        }

        return articleDslRepository.findArticleByCondition(searchType, searchKeyword, pageable);
    }

    public void updateArticle(ArticleRequest articleRequest, Long articleId, UserDto userDto) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new CustomException(ErrorCode.ARTICLE_NOT_EXISTS)
        );

        if (!Objects.equals(article.getUser().getId(), userDto.getUserId())) {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }

        articleRepository.save(articleRequest.toDto(userDto).toEntity());
    }

    public void like(Long articleId, UserDto userdto) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new CustomException(ErrorCode.ARTICLE_NOT_EXISTS)
        );

        // like duplication check
        boolean isLiked = articleLikeRepository.existsByUserIdAndArticleId(articleId, userdto.getUserId());

        if (isLiked) {
            throw new CustomException(ErrorCode.COMMENT_LIKE_DUPLICATE);
        }
        article.increaseLikeCount();
        articleLikeRepository.save(ArticleCount.of(userdto.toEntity(), article));
    }

    public void deleteArticleLike(Long articleId, Long userId) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new CustomException(ErrorCode.ARTICLE_NOT_EXISTS)
        );

        ArticleCount articleCount = articleLikeRepository.findByUserIdAndArticleId(articleId, userId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_LIKE_NOT_EXIST)
        );

        articleLikeRepository.deleteById(articleCount.getId());
        article.decreaseLikeCount();

        log.info("successfully deleted");
    }
}
