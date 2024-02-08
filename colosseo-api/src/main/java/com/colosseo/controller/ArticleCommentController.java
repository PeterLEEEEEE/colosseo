package com.colosseo.controller;

import com.colosseo.dto.articleComment.ArticleCommentRequest;
import com.colosseo.global.config.security.UserPrincipal;
import com.colosseo.model.user.User;
import com.colosseo.service.article.ArticleCommentService;
import com.colosseo.service.article.ArticleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "jwtAuth")
@RequestMapping("/api/v1")
@Tag(name = "게시글 댓글 API", description = "게시글 댓글 CRUD API")
public class ArticleCommentController {
    private final ArticleService articleService;
    private final ArticleCommentService articleCommentService;

    @PostMapping("/article/{articleId}/comments")
    public String postCommentToArticle(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long articleId,
            @Valid @RequestBody ArticleCommentRequest request
    ) {
        return articleCommentService.postArticleComment(request.toDto(userPrincipal.toDto()), articleId);
    }

    @PutMapping("/comments/{commentId}")
    @PreAuthorize("hasPermission(#commentId, 'ARTICLECOMMENT', 'UPDATE')")
    public String updateArticleComment(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long commentId,
            @Valid @RequestBody ArticleCommentRequest commentRequest
    ) {
        articleCommentService.updateArticleComment(commentRequest, userPrincipal.toDto());
        return "dd";
    }

    @DeleteMapping("/comments/{commentId}")
//    @PreAuthorize("hasPermission(#commentId, 'ARTICLECOMMENT', 'UPDATE')")
    public String deleteArticleComment(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long commentId
        ) {
        articleCommentService.deleteArticleComment(commentId);

        return "dd";
    }
}
