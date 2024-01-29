package com.colosseo.model.alarm;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AlarmDetails {
    // 게시글이나 댓글의 아이디가 들어갈 예정
    private Long targetId;
}
