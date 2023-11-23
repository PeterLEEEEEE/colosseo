package com.colosseo.controller;

import com.colosseo.dto.article.ArticleDto;
import com.colosseo.dto.article.ArticleRequest;
import com.colosseo.dto.article.ArticleResponse;
import com.colosseo.global.config.security.UserPrincipal;
import com.colosseo.global.enums.SearchType;

import com.colosseo.model.article.Article;
import com.colosseo.service.article.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "jwtAuth")
@RequestMapping("/api/v1")
@Tag(name = "게시글 API", description = "게시글 CRUD API")
public class ArticleController {

    private final ArticleService articleService;

//    @Operation(summary = "특정 게시글과 댓글 가져오기", description = "유저 로그인 API")
//    @GetMapping("/{articleId}")
//    @Cacheable(key = "#articleId", cacheNames = "article")
//    public String getPostWithComments(@PathVariable Long articleId) {
//        articleService.getArticle(articleId);
//        return "test";
//    }

    @PostMapping("/articles")
    @Operation(summary = "아티클 등록", description = "아티클 등록하는 API")
    public String postArticle(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody ArticleRequest articleRequest) {

        return articleService.postArticle(articleRequest.toDto(userPrincipal.toDto()));
    }

    @GetMapping("/articles/{articleId}")
    public ResponseEntity<ArticleDto> getArticleDetailWithComments(@PathVariable Long articleId) {
        ArticleDto articleDto = articleService.getArticleDetailWithComments(articleId);
        return ResponseEntity.ok().body(articleDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasPermission(#articleId, 'ARTICLE', 'DELETE')")
    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity<String> deleteArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        String result = articleService.deleteArticle(userPrincipal.getUserId(), articleId);
        return ResponseEntity.ok().body(result);
    }

//    @PreAuthorize("hasPermission(#articleId, 'Article', 'UPDATE')")
    @PutMapping("/articles/{articleId}")
    public String updateArticle(
            @PathVariable Long articleId,
            @Valid @RequestBody ArticleRequest articleRequest,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        articleService.updateArticle(articleRequest, articleId, userPrincipal.toDto());
        return "success";
    }

    @GetMapping("/articles")
    @Operation(summary = "페이지 별 아티클 가져오기", description = "아티클 페이지 가져오는 API, 검색: 작성자, 글 내용 한 페이지 당 10개의 아티클")
    public ResponseEntity<Page<ArticleResponse>> getArticles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10,  sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
            ) {
        return ResponseEntity.ok().body(articleService.getArticles(pageable, searchType, searchValue));
    }

    @PostMapping("/articles/{articleId}/likes")
    @Operation(summary = "아티클 좋아요 기능", description = "아티클 좋아요 API")
    public ResponseEntity<String> likeArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        articleService.like(articleId, userPrincipal.getUserId());
        return ResponseEntity.created(null).body("success");
    }
}
