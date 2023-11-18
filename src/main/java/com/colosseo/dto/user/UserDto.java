package com.colosseo.dto.user;

import com.colosseo.global.enums.AccountStatusType;
import com.colosseo.global.enums.RoleType;
import com.colosseo.model.user.User;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserDto {
    private Long userId;
    private String email;
    private String nickname;
    private AccountStatusType accountStatusType;
    private String password;
    private LocalDateTime createdAt;
    private RoleType roleType;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String modifiedBy;


    @Builder
    public UserDto(Long userId, String email, String nickname, AccountStatusType accountStatusType, String password, LocalDateTime createdAt, RoleType roleType, String createdBy, LocalDateTime updatedAt, String modifiedBy) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.accountStatusType = accountStatusType;
        this.password = password;
        this.createdAt = createdAt;
        this.roleType = roleType;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.modifiedBy = modifiedBy;
    }

    public static UserDto from(User user) {
        return UserDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
//                .createdAt(user.getCreatedAt())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .id(userId)
                .email(email)
                .password(password)
                .roleType(roleType)
                .build();
    }
}
