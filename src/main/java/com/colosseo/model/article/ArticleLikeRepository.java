package com.colosseo.model.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleLikeRepository extends JpaRepository<ArticleCount, Long> {

    boolean existsByUserIdAndArticleId(Long articleId, Long userId);

    Optional<ArticleCount> findByUserIdAndArticleId(Long articleId, Long userId);
}
