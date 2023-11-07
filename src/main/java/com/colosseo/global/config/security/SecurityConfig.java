package com.colosseo.global.config.security;

import com.colosseo.global.config.redis.RedisDao;
import com.colosseo.global.config.security.jwt.CustomAccessDeniedHandler;
import com.colosseo.global.config.security.jwt.TokenProvider;
import com.colosseo.global.config.security.oauth.CustomOAuth2UserService;
import com.colosseo.global.config.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.colosseo.global.config.security.oauth.OAuth2AuthenticationFailureHandler;
import com.colosseo.global.config.security.oauth.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntrypoint customAuthenticationEntrypoint;
//    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
//    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
//    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final TokenProvider tokenProvider;
    private final RedisDao redisDao;
    private final CorsConfig corsConfig;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new SCryptPasswordEncoder(
//                16,
//                8,
//                1,
//                32,
//                64
//        );
//    }

    @Bean
    public WebSecurityCustomizer configure() {
        // spring security 검사에서 제외할 목록, 사용하기를 권장하지 않음
        return (web) -> web.ignoring().requestMatchers(
                "/error",
//                "/api-docs/**",
//                "/v1/api-docs/**",
//                "/swagger-ui/**",
//                "/docs/**",
                "/favicon.ico",
//                "/oauth/**",
//                "/oauth2/**",
                "/api/v1/email-verification",
                "/api/v1/email-confirm/**",
                "/v3/api-docs/**" // 이거 설정안하면 스웨거가 안뜸;;
        );
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        firewall.setAllowSemicolon(true);
        firewall.setAllowUrlEncodedDoubleSlash(true);
        return firewall;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {
        http
            .addFilterBefore(corsConfig.corsFilter(), UsernamePasswordAuthenticationFilter.class)
//            .headers(headers -> headers.frameOptions(Customizer.withDefaults()))
            .formLogin().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .headers(headers -> headers.frameOptions().sameOrigin())
            .csrf().disable()
            .exceptionHandling(e -> {
                        e.accessDeniedHandler(customAccessDeniedHandler);
                        e.authenticationEntryPoint(customAuthenticationEntrypoint);
                    }
            ).authorizeHttpRequests(auth -> auth.requestMatchers(
                                        "/error",
                                        "/api-docs/**",
                                        "/v1/api-docs/**",
                                        "/swagger-ui/**",
                                        "/docs/**",
                                        "/oauth2/callback/**",
                                        "/oauth/**",
                                        "/favicon.ico",
                                        "/api/v1/login",
                                        "/api/v1/signup"
                ).permitAll()
                        .requestMatchers("/admin")
                        .access(new WebExpressionAuthorizationManager("hasRole('ADMIN') AND hasAuthority('WRITE')"))
                .anyRequest().authenticated())

                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository())
            .and()
                .redirectionEndpoint()
                // /oauth/kakao/redirect?code=
                .baseUri("/oauth2/callback/*")
            .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
            .and()
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .failureHandler(oAuth2AuthenticationFailureHandler());



        return http.addFilterBefore(new CustomAuthenticationFilter(tokenProvider, redisDao), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                cookieOAuth2AuthorizationRequestRepository()
        );
    }
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(cookieOAuth2AuthorizationRequestRepository());
    }
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }
}
