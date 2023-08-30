package com.colosseo.service.user;

import com.colosseo.dto.user.TokenRequestDto;
import com.colosseo.dto.user.TokenResponseDto;
import com.colosseo.dto.user.UserLoginRequestDto;
import com.colosseo.dto.user.UserSignupRequestDto;
import com.colosseo.exception.ErrorCode;
import com.colosseo.global.config.redis.RedisDao;
import com.colosseo.global.config.security.CustomAuthenticationProvider;
import com.colosseo.global.config.security.jwt.TokenProvider;
import com.colosseo.global.handler.CustomException;
import com.colosseo.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RedisDao redisDao;

    public TokenResponseDto userLogin(UserLoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        Authentication authentication = customAuthenticationProvider.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication.getName());
        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String userRegister(UserSignupRequestDto signupRequestDto) {
        if (userRepository.findByEmail(signupRequestDto.getEmail()).orElse(null) != null) {
            throw new CustomException(ErrorCode.USER_ALREADY_EXIST);
        }
        if (!signupRequestDto.getPassword().equals(signupRequestDto.getPasswordCheck())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_SAME);
        }

        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        userRepository.save(signupRequestDto.toEntity(encodedPassword));

        return "success";
    }

    public String logout(TokenRequestDto tokenRequestDto) {
        String accessToken = tokenRequestDto.getAccessToken().substring(7);
        if(!tokenProvider.validateToken(accessToken).equals("valid")) {
            throw new CustomException(ErrorCode.JWT_COMMON_ERROR);
        }

        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        Long expiration = tokenProvider.getTokenExpiration(accessToken);

        redisDao.setValues(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);

        return "정상적으로 로그아웃 되었습니다.";
    }
}
