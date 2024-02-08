package com.colosseo.controller;

import com.colosseo.dto.alarm.AlarmResponse;
import com.colosseo.dto.email.CodeRequestDto;
import com.colosseo.dto.user.TokenRequestDto;
import com.colosseo.dto.user.TokenResponseDto;
import com.colosseo.dto.user.UserLoginRequestDto;
import com.colosseo.dto.user.UserSignupRequestDto;
import com.colosseo.global.config.security.UserPrincipal;
import com.colosseo.service.email.EmailServiceImpl;
import com.colosseo.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
@Tag(name = "유저 API", description = "유저 인증 관련 API")
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

    @Operation(summary = "이메일 인증코드 요청 API", description = "유저 회원가입 시 이메일로 인증코드 요청하는 API")
    @PostMapping("/email-confirm")
    public ResponseEntity<String> emailConfirm(@RequestParam String email) throws Exception {
        return ResponseEntity.ok().body(emailService.sendMessage(email));
    }

    @Operation(summary = "이메일 인증코드 확인 API", description = "회원가입 시 이메일 인증코드 확인 API")
    @PostMapping("/email-verification")
    public ResponseEntity<Boolean> emailCode(@Valid @RequestBody CodeRequestDto codeRequestDto) {
        return ResponseEntity.ok().body(emailService.verifyCode(codeRequestDto));
    }

    @Operation(summary = "팔로우 API", description = "글 작성자를 팔로우하는 기능 API")
    @SecurityRequirement(name = "jwtAuth")
    @GetMapping("/follow/{userId}}")
    public ResponseEntity<String> followUser(
            @PathVariable Long userId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        userService.followUser(userId, userPrincipal.getUserId());
        return ResponseEntity.ok().body("follow success");
    }

    @Operation(summary = "새로운 좋아요나 댓글, 팔로우가 생겼을 시 알람 API", description = "신규 좋아요나 댓글, 팔로우에 대한 알람 리스트 API")
//    @PreAuthorize("hasRole('ROLE_USER') || hasPermission(#articleId, 'ARTICLE', 'DELETE')")
    @SecurityRequirement(name = "jwtAuth")
    @GetMapping("/alarm")
    public ResponseEntity<Page<AlarmResponse>> alarm(
            @PageableDefault(size = 10,  sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return ResponseEntity.ok().body(userService.alarmList(userPrincipal.toDto(), pageable));
    }
}
