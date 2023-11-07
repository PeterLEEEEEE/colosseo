package com.colosseo.service.article;

import com.colosseo.dto.article.ArticleDto;
import com.colosseo.dto.article.ArticleRequestDto;
import com.colosseo.exception.CustomException;
import com.colosseo.exception.ErrorCode;
import com.colosseo.model.article.Article;
import com.colosseo.model.user.User;
import com.colosseo.repository.ArticleRepository;
import com.colosseo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    public String postArticle(ArticleDto articleDto) {
        User user = userRepository.findByEmail(articleDto.getUserDto().getEmail())
                .orElseThrow( () -> new CustomException(ErrorCode.USER_NOT_EXIST));

        articleRepository.save(articleDto.toEntity(user));

        return "success";
    }

//    public void getArticle(Long articleId) {
//        articleRepository.findById(articleId)
//                .map(ArticleWithCommentsDto::from)
//    }
}
