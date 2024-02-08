package com.colosseo.global.config.security;

import com.colosseo.global.config.redis.RedisDao;
import com.colosseo.global.config.security.jwt.CustomAccessDeniedHandler;
import com.colosseo.global.config.security.jwt.TokenProvider;
import com.colosseo.global.config.security.oauth.CustomOAuth2UserService;
import com.colosseo.global.config.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.colosseo.global.config.security.oauth.OAuth2AuthenticationFailureHandler;
import com.colosseo.global.config.security.oauth.OAuth2AuthenticationSuccessHandler;
import com.colosseo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntrypoint customAuthenticationEntrypoint;
//    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
//    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
//    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final TokenProvider tokenProvider;
    private final RedisDao redisDao;
    private final CorsConfig corsConfig;

    private final String[] permittedUrl = {
            "/",
            "/error",
            "/api-docs/**",
            "/v1/api-docs/**",
            "/swagger-ui/**",
            "/docs/**",
            "/oauth/**",
            "/oauth2/**",
            "/oauth2/callback/**",
            "/favicon.ico",
            "/api/v1/login",
            "/api/v1/signup",
//            "/actuator/**",
//            "/api/v1/health-check"
    };

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
                "/favicon.ico",
//                "/oauth/**",
//                "/oauth2/**",
                "/api/v1/email-verification",
                "/api/v1/email-confirm/**",
                "/actuator/**",
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
            )
            .authorizeHttpRequests(auth ->
                auth.requestMatchers(permittedUrl).permitAll()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                    .requestMatchers("/admin/**")
//                        .access(new WebExpressionAuthorizationManager("hasRole('ROLE_ADMIN') AND hasAuthority('WRITE')"))
                    .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> {
                oauth2
//                        .successHandler(oAuth2AuthenticationSuccessHandler())
                        .successHandler(successHandler())
                        .failureHandler(oAuth2AuthenticationFailureHandler())
                        .authorizationEndpoint(authorizationEndpoint -> {
                            authorizationEndpoint.baseUri("/oauth2/authorization");
                        })
                        .redirectionEndpoint(redirection -> {
                            redirection.baseUri("/oauth2/callback/*");
                        })
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService)
                        );

            });

//                .oauth2Login()
//                .authorizationEndpoint()
//                .baseUri("/oauth2/authorization")
//                .authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository())
//            .and()
//                .redirectionEndpoint()
//                // /oauth/kakao/redirect?code=
//                .baseUri("/oauth2/callback/*")
//            .and()
//                .userInfoEndpoint()
//                .userService(customOAuth2UserService)
//            .and()
//                .successHandler(oAuth2AuthenticationSuccessHandler())
//                .failureHandler(oAuth2AuthenticationFailureHandler());


        // hmm
        return http.addFilterBefore(new CustomAuthenticationFilter(tokenProvider, redisDao), RequestCacheAwareFilter.class)
                .build();
    }

//    @Bean
//    public CustomEmailPasswordFilter emailPasswordFilter() {
//        CustomEmailPasswordFilter filter = new CustomEmailPasswordFilter("/api/v1/login", objectMapper);
////        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/"));
//        filter.setAuthenticationFailureHandler(new LoginFailureHandler(objectMapper));
//        filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
//
//         return filter;
//    }
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new OAuth2AuthenticationSuccessHandler(tokenProvider);
    }
//    @Bean
//    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
//        return new OAuth2AuthenticationSuccessHandler(
//                tokenProvider,
//                cookieOAuth2AuthorizationRequestRepository()
//        );
//    }
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(cookieOAuth2AuthorizationRequestRepository());
    }
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }
}
