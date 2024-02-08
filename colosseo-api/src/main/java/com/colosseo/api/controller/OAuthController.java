package com.colosseo.api.controller;

import io.lettuce.core.dynamic.annotation.Param;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    @GetMapping("/kakao/redirect/test")
    public String kakaoLogin(@RequestParam String code) {
        return code;
    }
}
