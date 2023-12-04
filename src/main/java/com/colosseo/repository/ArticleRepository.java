package com.colosseo.repository;

import com.colosseo.model.article.Article;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @EntityGraph(attributePaths = {"articleCommentList"})
    Optional<Article> findById(Long articleId);
    Page<Article> findById(@NonNull Long articleId, Pageable pageable);
//    Page<Article> findAll(Pageable pageable);
//    void deleteByIdAndUser_UserId(Long articleId, Long userId);
}
