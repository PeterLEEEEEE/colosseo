package com.colosseo.service.article;

import com.colosseo.dto.article.ArticleDto;
import com.colosseo.dto.article.ArticleResponse;
import com.colosseo.exception.CustomException;
import com.colosseo.exception.ErrorCode;
import com.colosseo.model.article.Article;
import com.colosseo.global.enums.SearchType;
import com.colosseo.repository.ArticleDslRepository;
import com.colosseo.repository.ArticleRepository;
import com.colosseo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.micrometer.common.util.StringUtils.isBlank;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleDslRepository articleDslRepository;
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
}
