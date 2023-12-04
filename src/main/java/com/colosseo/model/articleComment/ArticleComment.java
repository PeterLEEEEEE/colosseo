package com.colosseo.model.articleComment;

import com.colosseo.model.BaseEntity;
import com.colosseo.model.article.Article;
import com.colosseo.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "IDX_ARTICLE_COMMENT_CREATED_BY", columnList = "createdBy"),
        @Index(name = "IDX_ARTICLE_COMMENT_CREATED_AT", columnList = "createdAt"),
        @Index(name = "IDX_ARTICLE_COMMENT_ARTICLE_ID", columnList = "article_id")
})
public class ArticleComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @JoinColumn(name="parent_comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleComment parentComment; // 부모 댓글 식별

    @ToString.Exclude // 자기 자신을 보고 있으면 무한으로 참조하니까 이거 넣어야 함
    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ArticleComment> childComments = new ArrayList<>();

    @Column(nullable = false, length = 255)
    private String comment;

    @Setter
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="article_id", nullable = false)
    private Article article;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Builder
    public ArticleComment(ArticleComment parentComment, String comment, Article article, User user) {
        this.parentComment = parentComment;
        this.comment = comment;
        this.article = article;
        this.user = user;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }

    public void addChildComment(ArticleComment child) {
        child.setParentComment(this.getParentComment());
        this.getChildComments().add(child);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
