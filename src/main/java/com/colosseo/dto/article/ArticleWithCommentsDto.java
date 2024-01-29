package com.colosseo.dto.article;

import com.colosseo.dto.articleComment.ArticleCommentDto;
import com.colosseo.dto.user.UserDto;
import com.colosseo.model.article.Article;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ArticleWithCommentsDto {
    private Long articleId;
    private UserDto userDto;
    private List<ArticleCommentDto> articleCommentDtoList;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private String createdBy;

    @Builder
    public ArticleWithCommentsDto(Long articleId, UserDto userDto, List<ArticleCommentDto> articleCommentDtoList, String title, String content, Integer viewCount, Integer likeCount, LocalDateTime createdAt, String createdBy) {
        this.articleId = articleId;
        this.userDto = userDto;
        this.articleCommentDtoList = articleCommentDtoList;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public static ArticleWithCommentsDto from(Article article) {
        return new ArticleWithCommentsDto(
            article.getId(),
            UserDto.from(article.getUser()),
            article.getArticleCommentList().stream()
                    .map(ArticleCommentDto::from)
                    .collect(Collectors.toCollection(ArrayList::new)),
            article.getTitle(),
            article.getContent(),
            article.getViewCount(),
            article.getLikeCount(),
            article.getCreatedAt(),
            article.getCreatedBy()
        );
    }
}
