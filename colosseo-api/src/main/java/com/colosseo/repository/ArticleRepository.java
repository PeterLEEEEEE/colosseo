package com.colosseo.repository;

import com.colosseo.model.article.Article;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
//    @EntityGraph(attributePaths = {"articleCommentList"})
    Optional<Article> findById(Long articleId);
    Page<Article> findById(@NonNull Long articleId, Pageable pageable);

//    List<Article> findTop5ByCreatedAtAndOrderByViewCountDesc();
    @Query(value = "SELECT a.view_count from article a where a.id = :articleId", nativeQuery = true)
    Integer findViewCountById(@Param("articleId") Long articleId);
//    @Query(value = "SELECT u.name FROM User u WHERE u.id = :userId", nativeQuery = true)
//    String findUserNameById(@Param("userId") Long userId);
//    void deleteByIdAndUser_UserId(Long articleId, Long userId);
}
