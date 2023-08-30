package com.colosseo.global.config.security.jwt;

import com.colosseo.exception.ErrorCode;
import com.colosseo.global.config.redis.RedisDao;
import com.colosseo.global.config.security.CustomUserDetailsService;
import com.colosseo.global.config.security.SecurityUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenProvider implements InitializingBean {
    private final CustomUserDetailsService customUserDetailsService;
    private final RedisDao redisDao;
    private Key key;

//    @Value("${jwt.security.header}")
    private String header;

    @Value("${jwt.security.key}")
    private String secretKey;
    private static final String tokenPrefix = "Bearer ";
    private static final long accessTokenExpireTime = Duration.ofDays(1).toMillis();
    private static final long refreshTokenExpireTime = Duration.ofDays(14).toMillis();

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String resolveToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith(tokenPrefix)) {
            return token.substring(7);
        }
        return null;
    }

    public String generateToken(Authentication authentication, long expirationTimeMillis) {
        Claims claims = Jwts.claims();
        String authorities = SecurityUtils.getAuthorities(authentication);
        claims.put("auth", authorities);
        claims.put("sub", authentication.getName());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateToken(String username, long expirationTimeMillis) {
        Claims claims = Jwts.claims();
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createAccessToken(String username) {
        String accessToken = generateToken(username, accessTokenExpireTime);
        return tokenPrefix + accessToken;
    }

    public String createAccessToken(Authentication authentication) {
        String accessToken = generateToken(authentication, accessTokenExpireTime);
        return tokenPrefix + accessToken;
    }

    public String createRefreshToken(String username) {
        String refreshToken = generateToken(username, refreshTokenExpireTime);
        log.info("refresh token: {}", refreshToken);
        redisDao.setValues(username, refreshToken, Duration.ofDays(14));

        return tokenPrefix + refreshToken;
    }

    public String validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return "valid";
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            return ErrorCode.JWT_WRONG_SIGNATURE.getCode();
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            return ErrorCode.JWT_EXPIRED_TOKEN.getCode();
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            return ErrorCode.JWT_UNSUPPORTED_TOKEN.getCode();
        } catch (IllegalArgumentException e) {
            log.info("잘못된 JWT 토큰입니다.");
            return ErrorCode.JWT_INVALID_TOKEN.getCode();
        }
    }

    public static Map<String, String> getPayloadByToken(String token) {
        try {
            String [] splitJwt = token.split("\\.");
            Base64.Decoder decoder = Base64.getDecoder();
            String payload = new String(decoder.decode(splitJwt[1].getBytes()));

            return new ObjectMapper().readValue(payload, Map.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JwtException("잘못된 토큰 양식입니다. - sub 부재");
        }
    }

    public Authentication getAuthentication(String token) {
        Map<String, String> payloadMap = getPayloadByToken(token);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(payloadMap.get("sub"));

        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    public Long getTokenExpiration(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }
}
