package com.colosseo.global.enums;

import lombok.Getter;

public enum RoleType {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    @Getter
    private final String name;

    RoleType(String name) {
        this.name = name;
    }
}
