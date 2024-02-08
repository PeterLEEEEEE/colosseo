package com.colosseo.model.user;

import com.colosseo.global.enums.ProviderType;
import com.colosseo.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@ToString(callSuper = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auth extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Setter
    @OneToOne(optional = false)
    private User user;

    private Boolean isLinked; // local 계정과의 연동 여부
    private String socialCode;
}
