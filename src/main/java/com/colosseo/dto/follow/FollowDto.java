package com.colosseo.dto.follow;

import com.colosseo.model.article.Article;
import com.colosseo.model.follow.Follow;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowDto {
    private Long followerId;
    private Long followeeId;

    @Builder
    public FollowDto(Long followerId, Long followeeId) {
        this.followerId = followerId;
        this.followeeId = followeeId;
    }

    public Follow toEntity() {
        return Follow.builder()
                .followeeId(followeeId)
                .followerId(followerId)
                .build();
    }
}
