package com.colosseo.service.article;

import com.colosseo.dto.articleComment.ArticleCommentDto;
import com.colosseo.dto.articleComment.ArticleCommentRequest;
import com.colosseo.dto.user.UserDto;
import com.colosseo.exception.CustomException;
import com.colosseo.exception.ErrorCode;
import com.colosseo.model.article.Article;
import com.colosseo.model.articleComment.ArticleComment;
import com.colosseo.model.user.User;
import com.colosseo.repository.ArticleCommentRepository;
import com.colosseo.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.events.Comment;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;

    public String postArticleComment(ArticleCommentDto articleCommentDto, Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new CustomException(ErrorCode.ARTICLE_NOT_EXISTS)
        );
//        ArticleCommentDto articleCommentDto = ArticleCommentDto.builder()
//                .articleDto(article.toDto())
//                .userDto(userDto)
//                .comment(articleCommentRequest.getComment())
//                .build();
        ArticleComment articleComment = articleCommentDto.toEntity(article);

        if (articleComment.getParentCommentId() != null) {
            ArticleComment parentComment = articleCommentRepository.findById(articleComment.getParentCommentId())
                    .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_EXISTS));
            parentComment.addChildComment(articleComment);
        } else {
            articleCommentRepository.save(articleComment);
        }

        return "successfully written(article comment)";
    }

    public void updateArticleComment(ArticleCommentRequest articleCommentRequest, UserDto userDto) {

    }

    public void deleteArticleComment(Long commentId) {
        ArticleComment articleComment = articleCommentRepository.findById(commentId)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.COMMENT_NOT_EXISTS)
                );

        articleCommentRepository.deleteById(articleComment.getId());
    }
}
