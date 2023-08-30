package com.colosseo.controller;

import com.colosseo.dto.article.ArticleRequestDto;
import com.colosseo.global.config.security.UserPrincipal;
import com.colosseo.service.article.ArticleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "jwtAuth")
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;
    @GetMapping("/{articleId}")
    public String getPostWithComments(@PathVariable Long articleId) {
        return "test";
    }

    @PostMapping("/post")
    public String postArticle(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody ArticleRequestDto articleRequestDto) {
        articleService.postArticle(articleRequestDto.toDto(userPrincipal.toDto()));
        return "";
    }

}
