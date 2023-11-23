package com.colosseo.service.article;

import com.colosseo.dto.article.ArticleDto;
import com.colosseo.dto.article.ArticleRequest;
import com.colosseo.dto.article.ArticleResponse;
import com.colosseo.dto.user.UserDto;
import com.colosseo.exception.CustomException;
import com.colosseo.exception.ErrorCode;
import com.colosseo.model.article.Article;
import com.colosseo.global.enums.SearchType;
import com.colosseo.model.article.ArticleLike;
import com.colosseo.model.article.ArticleLikeRepository;
import com.colosseo.model.user.User;
import com.colosseo.repository.ArticleDslRepository;
import com.colosseo.repository.ArticleRepository;
import com.colosseo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static io.micrometer.common.util.StringUtils.isBlank;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleDslRepository articleDslRepository;
    private final ArticleLikeRepository articleLikeRepository;
    private final UserRepository userRepository;

    public String postArticle(ArticleDto articleDto) {

        articleRepository.save(articleDto.toEntity());

        return "success";
    }

//    public void getArticle(Long articleId) {
//        articleRepository.findById(articleId)
//                .map(ArticleWithCommentsDto::from)
//    }

    @Transactional(readOnly = true)
    public ArticleDto getArticleDetailWithComments(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new CustomException(ErrorCode.ARTICLE_NOT_EXISTS)
        );
        return article.toDto();
//        return "dd";
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

    public void like(Long articleId, Long userId) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new CustomException(ErrorCode.ARTICLE_NOT_EXISTS)
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_EXIST)
        );

        // like duplication check
        boolean isLiked = articleLikeRepository.existsByUserIdAndArticleId(articleId, userId);

        if (isLiked) {
            throw new CustomException(ErrorCode.COMMENT_LIKE_DUPLICATE);
        }

        articleLikeRepository.save(ArticleLike.of(user, article));
    }

    public void unlike(Long articleId, Long userId) {

    }
}
