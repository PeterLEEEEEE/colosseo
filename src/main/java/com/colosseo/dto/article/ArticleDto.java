package com.colosseo.dto.article;

import com.colosseo.dto.user.UserDto;
import com.colosseo.model.article.Article;
import com.colosseo.model.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleDto {
    private Long id;
    private UserDto userDto;
    private String title;
    private String content;
    private String nickname;
    private Integer likes;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String modifiedBy;

    @Builder
    public ArticleDto(Long id, UserDto userDto, String title, String content, String nickname, Integer likes, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String modifiedBy) {
        this.id = id;
        this.userDto = userDto;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.likes = likes;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.modifiedBy = modifiedBy;
    }

    public Article toEntity(User user) {
        return Article.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
