package com.colosseo.repository;

import com.colosseo.model.article.Article;
import com.colosseo.model.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follow, Long> {

}
