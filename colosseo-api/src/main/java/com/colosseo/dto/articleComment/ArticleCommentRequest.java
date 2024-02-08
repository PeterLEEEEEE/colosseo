package com.colosseo.dto.articleComment;

import com.colosseo.dto.article.ArticleDto;
import com.colosseo.dto.user.UserDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class ArticleCommentRequest {
    private Long parentCommentId;

    @Length(min = 10, max = 250, message = "댓글은 최소 10자 최대 250자까지 입력 가능합니다.")
    @NotBlank(message = "댓글 내용은 반드시 입력되어야 합니다.")
    private String comment;

    @Builder
    public ArticleCommentRequest(Long parentCommentId, String comment) {
        this.parentCommentId = parentCommentId;
        this.comment = comment;
    }

    public ArticleCommentDto toDto(UserDto userDto) {
        return ArticleCommentDto.builder()
                .parentCommentId(parentCommentId)
                .userDto(userDto)
//                .articleDto(articleDto)
                .comment(comment)
                .build();
    }
}
