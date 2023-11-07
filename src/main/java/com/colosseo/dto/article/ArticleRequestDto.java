package com.colosseo.dto.article;

import com.colosseo.dto.user.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
