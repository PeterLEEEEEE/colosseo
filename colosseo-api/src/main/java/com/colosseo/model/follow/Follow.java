package com.colosseo.model.follow;

import com.colosseo.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "IDX_FOLLOW_CREATED_AT", columnList = "createdAt"),
})
public class Follow extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 팔로우 당하는 사람의 아이디
    private Long followeeId;

    // 팔로우 하는 사람의 아이디
    private Long followerId;

    @Builder
    public Follow(Long followeeId, Long followerId) {
        this.followeeId = followeeId;
        this.followerId = followerId;
    }
}
