package com.colosseo.repository;

import com.colosseo.model.article.Article;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findById(@NonNull Long articleId);
//    void deleteByIdAndUser_UserId(Long articleId, Long userId);
}
