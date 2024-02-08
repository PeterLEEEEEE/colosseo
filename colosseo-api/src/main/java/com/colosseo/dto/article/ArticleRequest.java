package com.colosseo.dto.article;

import com.colosseo.dto.user.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleRequest {
    private String title;
    private String content;

    @Builder
    public ArticleRequest(String title, String content) {
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
