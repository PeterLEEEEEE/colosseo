package com.colosseo.dto.email;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class CodeRequestDto {
    @NonNull
    private String email;
    @NonNull
    private String code;

    @Builder
    public CodeRequestDto(String email, String code) {
        this.email = email;
        this.code = code;
    }
}
