package com.colosseo.repository;

import com.colosseo.model.article.Article;
import com.colosseo.model.articleComment.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
