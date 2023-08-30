package com.colosseo.dto.user;

import com.colosseo.global.enums.AccountStatusType;
import com.colosseo.global.enums.RoleType;
import com.colosseo.model.user.User;
import lombok.Getter;

@Getter
public class UserSignupRequestDto {
    private String nickname;
    private String password;
    private String passwordCheck;
    private String email;

    public User toEntity(String encodedPassword) {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .password(encodedPassword)
                .accountStatusType(AccountStatusType.ACTIVE)
                .roleType(RoleType.USER)
                .build();
    }
}
