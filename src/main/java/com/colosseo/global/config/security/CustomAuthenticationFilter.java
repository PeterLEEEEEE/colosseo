package com.colosseo.global.config.security;

import com.colosseo.global.config.redis.RedisDao;
import com.colosseo.global.config.security.jwt.TokenProvider;
import com.colosseo.global.utils.UrlUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;


//@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final RedisDao redisDao;

    private String header = "Authorization";
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 헤더에서 토큰을 가져옴
        String requestHeader = request.getHeader(header);
        String requestURI = request.getRequestURI();

//        String[] permittedUrls = UrlUtils.permittedUrl;
//
//        boolean isPermitted = Arrays.asList(permittedUrls)
//                .contains(requestURI);

        String token = tokenProvider.resolveToken(requestHeader);
        String jwtException = tokenProvider.validateToken(token);

        log.info(requestURI);

        if (token != null && jwtException.equals("valid")) {
            String isLoggedOut = redisDao.getValues(token);

            if (ObjectUtils.isEmpty(isLoggedOut)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Security Context 에 '{}' 인증 정보를 저장했습니다, uri: {}", tokenProvider.getSub(token), requestURI);
            } else {
                log.info("유효하지 않은 토큰입니다. token blacklist");
            }
        }
//        if (StringUtils.hasText(token)) {
//
//            String isLoggedOut = redisDao.getValues(token);
//
//            if (ObjectUtils.isEmpty(isLoggedOut)) {
//                if (jwtException.equals("valid")) {
//                    // db I/O
//                    Authentication authentication = tokenProvider.getAuthentication(token);
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    log.info("Security Context 에 '{}' 인증 정보를 저장했습니다, uri: {}", tokenProvider.getSub(token), requestURI);
//                } else {
//                    log.info("uri: {}", requestURI);
//                }
//            } else {
//                log.info("유효하지 않은 토큰입니다. token blacklist");
//            }
//        }

        request.setAttribute("jwt_exception", jwtException);

        filterChain.doFilter(request, response);
    }

}
