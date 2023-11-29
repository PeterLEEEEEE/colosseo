package com.colosseo.model.article;

import com.colosseo.model.BaseTimeEntity;
import com.colosseo.model.user.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ArticleCount extends BaseTimeEntity {
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

    // Redis Cache를 아티클에 적용하고 싶은데 count 값이 계속 업데이트되므로 분리를 시도하여 테스트 해보려 함

    @Column(columnDefinition = "int default 0")
    private Integer viewCount;

    @Column(columnDefinition = "int default 0")
    private Integer likeCount;

    @Builder
    public ArticleCount(User user, Article article, Integer viewCount, Integer likeCount) {
        this.user = user;
        this.article = article;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }


    public static ArticleCount of(User user, Article article) {
        return ArticleCount.builder()
                .user(user)
                .article(article)
                .build();
    }
}
