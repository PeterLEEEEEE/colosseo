package com.colosseo.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponseDto {
    @Schema(name = "accessToken", example = "JWT token", description = "Authorization header, Bearer + access_token ")
    @NotBlank
    private String accessToken;
    @NotBlank
    private String refreshToken;

    @Builder
    public TokenResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
