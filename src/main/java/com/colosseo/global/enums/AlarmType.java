package com.colosseo.global.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AlarmType {
    NEW_FOLLOWER("new follower"),
    NEW_COMMENT_ARTICLE("new comment on your article"),
    NEW_LIKE_ARTICLE("new like on your article"),
    NEW_LIKE_COMMENT("new like on your comment");

    private final String alarmText;
}
