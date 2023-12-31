package com.colosseo.dto.article;

import com.colosseo.dto.user.UserDto;
import com.colosseo.model.article.Article;
import com.colosseo.model.articleComment.ArticleComment;
import com.colosseo.model.user.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ArticleDto {
    private Long articleId;
    private UserDto userDto;
    private List<ArticleComment> articleComments;
    private String title;
    private String content;
    private String nickname;
    private Integer views;
    private Integer likes;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String modifiedBy;

    @Builder
    @QueryProjection
    public ArticleDto(Long articleId, UserDto userDto, String title, String content, String nickname, Integer views, Integer likes, List<ArticleComment> articleComments, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String modifiedBy) {
        this.articleId = articleId;
        this.userDto = userDto;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.views = views;
        this.likes = likes;
        this.articleComments = articleComments;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.modifiedBy = modifiedBy;
    }

    public Article toEntity() {

        return Article.builder()
                .title(title)
                .content(content)
                .user(userDto.toEntity())
                .articleCommentList(articleComments)
                .viewCount(views)
                .likeCount(likes)
                .build();
    }

    public static ArticleDto from(Article article) {
        return ArticleDto.builder()
                .title(article.getTitle())
                .content(article.getContent())
                .nickname(article.getUser().getNickname())
                .build();
    }
}
