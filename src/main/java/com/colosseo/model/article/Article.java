package com.colosseo.model.article;

import com.colosseo.model.BaseEntity;
import com.colosseo.model.articleComment.ArticleComment;
import com.colosseo.model.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @ToString.Exclude // 순환 참조 방지
    @OrderBy("id ASC, createdAt DESC")
    @OneToMany(mappedBy="article", cascade = CascadeType.ALL)
    private List<ArticleComment> articleCommentList = new ArrayList<>();

    @ToString.Exclude
    @ManyToOne(optional = false)
    private User user;
}
