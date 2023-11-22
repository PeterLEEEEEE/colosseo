package com.colosseo.model.article;

import com.colosseo.dto.article.ArticleDto;
import com.colosseo.model.BaseEntity;
import com.colosseo.model.articleComment.ArticleComment;
import com.colosseo.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "createdBy"),
        @Index(columnList = "createdAt")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 256)
    private String title;

    @Column(nullable = false, length = 2047)
    private String content;

    private Long likeCount;

//    private String author;

    @ToString.Exclude // 순환 참조 방지
    @OrderBy("id ASC, createdAt DESC")
    @OneToMany(mappedBy="article", cascade = CascadeType.ALL)
    private List<ArticleComment> articleCommentList = new ArrayList<>();

    @ToString.Exclude
    @ManyToOne(optional = false)
    private User user;

    @Builder
    public Article(Long id, String title, String content, Long likeCount, List<ArticleComment> articleCommentList, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
//        this.author = author;
        this.likeCount = likeCount;
        this.articleCommentList = articleCommentList;
        this.user = user;
    }

    public Long getUserId() {
        return this.user.getId();
    }

    public ArticleDto toDto() {
        return ArticleDto.builder()
                .articleId(id)
                .likes(likeCount)
                .userDto(getUser().toDto())
                .title(title)
                .content(content)
                .createdAt(getCreatedAt())
                .modifiedBy(String.valueOf(getUpdatedAt()))
                .build();
    }

    public void addComment(ArticleComment articleComment) {
        articleComment.setArticle(this);
        this.articleCommentList.add(articleComment);
    }
}
