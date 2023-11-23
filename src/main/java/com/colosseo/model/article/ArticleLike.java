package com.colosseo.model.article;

import com.colosseo.model.BaseEntity;
import com.colosseo.model.BaseTimeEntity;
import com.colosseo.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ArticleLike extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Builder
    public ArticleLike(User user, Article article) {
        this.user = user;
        this.article = article;
    }


    public static ArticleLike of(User user, Article article) {
        return ArticleLike.builder()
                .user(user)
                .article(article)
                .build();
    }
}
