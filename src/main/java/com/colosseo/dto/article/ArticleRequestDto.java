package com.colosseo.dto.article;

import com.colosseo.dto.user.UserDto;
import lombok.Builder;

public class ArticleRequestDto {
    private String title;
    private String content;

    @Builder
    public ArticleRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public ArticleDto toDto(UserDto userDto) {
        return ArticleDto.builder()
                .title(title)
                .content(content)
                .userDto(userDto)
                .build();
    }
}
