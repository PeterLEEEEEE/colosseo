package com.colosseo.dto.article;

import com.colosseo.dto.articleComment.ArticleCommentDto;
import com.colosseo.dto.user.UserDto;
import com.colosseo.model.article.Article;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ArticleWithCommentsDto {
    private Long articleId;
    private UserDto userDto;
    private List<ArticleCommentDto> articleCommentDtoList;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String createdBy;

    public static ArticleWithCommentsDto from(Article article) {
        return new ArticleWithCommentsDto(
            article.getId(),
            UserDto.from(article.getUser()),
            article.getArticleCommentList().stream()
                    .map(ArticleCommentDto::from)
                    .collect(Collectors.toCollection(ArrayList::new)),
            article.getTitle(),
            article.getContent(),
            article.getCreatedAt(),
            article.getCreatedBy()
        );
    }
}
