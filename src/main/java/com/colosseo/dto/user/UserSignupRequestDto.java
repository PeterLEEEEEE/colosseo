package com.colosseo.dto.user;

import com.colosseo.global.enums.AccountStatusType;
import com.colosseo.global.enums.ProviderType;
import com.colosseo.global.enums.RoleType;
import com.colosseo.model.user.User;
import com.colosseo.validation.EmailUnique;
import com.colosseo.validation.user.PasswordPattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserSignupRequestDto {

    @NotBlank(message = "닉네임은 필수 입력입니다.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @PasswordPattern
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String passwordCheck;

    @NotBlank(message = "이메일 형식이 올바르지 않습니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @EmailUnique
    private String email;

    public User toEntity(String encodedPassword) {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .password(encodedPassword)
                .providerType(ProviderType.LOCAL)
                .accountStatusType(AccountStatusType.ACTIVE)
                .roleType(RoleType.USER)
                .build();
    }
}
