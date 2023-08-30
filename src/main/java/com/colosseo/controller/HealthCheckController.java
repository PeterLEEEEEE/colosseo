package com.colosseo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "예제 API", description = "Swagger 테스트용 API")
@RestController
@Slf4j
@SecurityRequirement(name = "jwtAuth")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HealthCheckController {
    /**
     * loadbalance health check  시 활용.
     * @return "Test Colloseo" 200 ok
     */
    @Operation(summary = "서버 헬스체크", description = "앱 서버 상태 확인")
    @GetMapping("/health-check")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test Colosseo");
    }
}
