package com.colosseo.dto.articleComment;

import com.colosseo.dto.article.ArticleDto;
import com.colosseo.dto.article.ArticleWithCommentsDto;
import com.colosseo.dto.user.UserDto;
import com.colosseo.model.article.Article;
import com.colosseo.model.articleComment.ArticleComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleCommentDto {
    private Long id;
    private Long articleId;
    private Long parentCommentId;
    private UserDto userDto;
    private ArticleDto articleDto;
    private String comment;
    private LocalDateTime createdAt;
    private String createBy;
    private LocalDateTime modifiedAt;

    @Builder
    public ArticleCommentDto(Long id, Long articleId, Long parentCommentId, UserDto userDto, ArticleDto articleDto, String comment, LocalDateTime createdAt, String createBy, LocalDateTime modifiedAt) {
        this.id = id;
        this.articleId = articleId;
        this.parentCommentId = parentCommentId;
        this.userDto = userDto;
        this.articleDto = articleDto;
        this.comment = comment;
        this.createdAt = createdAt;
        this.createBy = createBy;
        this.modifiedAt = modifiedAt;
    }


    public ArticleComment toEntity(Article article) {
//        if (parentCommentId == null || parentCommentId == 0L) {
//            parentCommentId = id;
//        }
        return ArticleComment.builder()
                .user(userDto.toEntity())
                .article(article)
                .parentCommentId(parentCommentId)
                .comment(comment)
                .build();
    }

    public static ArticleCommentDto from(ArticleComment entity) {

        return ArticleCommentDto.builder()
                .articleId(entity.getArticle().getId())
                .parentCommentId(entity.getParentCommentId())
                .userDto(entity.getUser().toDto())
                .comment(entity.getComment())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
