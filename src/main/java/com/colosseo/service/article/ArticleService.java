package com.colosseo.service.article;

import com.colosseo.dto.article.ArticleDto;
import com.colosseo.dto.article.ArticleRequestDto;
import com.colosseo.model.article.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    public String postArticle(ArticleDto articleDto) {
        return "test";
    }
}
