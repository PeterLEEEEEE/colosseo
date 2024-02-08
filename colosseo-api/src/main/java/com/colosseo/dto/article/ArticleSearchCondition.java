package com.colosseo.dto.article;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleSearchCondition {

    private String title;
    private String content;
    private String nickname;

    @Builder
    public ArticleSearchCondition(String title, String content, String nickname) {
        this.title = title;
        this.content = content;
        this.nickname = nickname;
    }
}
