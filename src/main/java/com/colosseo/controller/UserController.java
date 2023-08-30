package com.colosseo.controller;

import com.colosseo.dto.email.CodeRequestDto;
import com.colosseo.dto.user.TokenRequestDto;
import com.colosseo.dto.user.TokenResponseDto;
import com.colosseo.dto.user.UserLoginRequestDto;
import com.colosseo.dto.user.UserSignupRequestDto;
import com.colosseo.service.email.EmailService;
import com.colosseo.service.email.EmailServiceImpl;
import com.colosseo.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class UserController {
    private final UserService userService;
    private final EmailServiceImpl emailService;

    @Operation(summary = "유저 로그인", description = "유저 로그인 API")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody UserLoginRequestDto loginRequestDto) {
        return ResponseEntity.ok().body(userService.userLogin(loginRequestDto));
    }
    @Operation(summary = "회원가입", description = "유저 회원가입 API")
    @PostMapping("/signup")
    public ResponseEntity<String> register(@Valid @RequestBody UserSignupRequestDto signupRequestDto) {
        return ResponseEntity.created(null).body(userService.userRegister(signupRequestDto));
    }

    // 로그아웃
    @Operation(summary = "유저 로그아웃", description = "유저 로그아웃 API")
    @PostMapping("/logout")
    public ResponseEntity<String> userLogout(@Valid @RequestBody TokenRequestDto tokenDto) {
        return ResponseEntity.ok().body(userService.logout(tokenDto));
    }

    @PostMapping("/email-confirm")
    public ResponseEntity<String> emailConfirm(@RequestParam String email) throws Exception {
        return ResponseEntity.ok().body(emailService.sendMessage(email));
    }

    @PostMapping("/email-verification")
    public ResponseEntity<Boolean> emailCode(@Valid @RequestBody CodeRequestDto codeRequestDto) {
        return ResponseEntity.ok().body(emailService.verifyCode(codeRequestDto));
    }
}
