package com.colosseo.model.user;

import com.colosseo.global.enums.AccountStatusType;
import com.colosseo.global.enums.RoleType;
import com.colosseo.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@ToString(callSuper = true) // 부모 클래스의 필드도 가져옴 -> BaseEntity도 가져옴
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String username;

    private String email;

    private String nickname;

    private String password;

    private LocalDateTime loginAt = null;

    private Boolean isVerified = false;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    @Column(name="account_status")
    private AccountStatusType accountStatusType;

    @Builder
    public User(String email, String nickname, String memo, String password, LocalDateTime loginAt, Boolean isVerified, RoleType roleType, AccountStatusType accountStatusType) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.loginAt = loginAt;
        this.isVerified = isVerified;
        this.roleType = roleType;
        this.accountStatusType = accountStatusType;
    }
}
