package com.colosseo.global.config.security;

import com.colosseo.global.config.redis.RedisDao;
import com.colosseo.global.config.security.jwt.CustomAccessDeniedHandler;
import com.colosseo.global.config.security.jwt.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntrypoint customAuthenticationEntrypoint;
    private final ObjectMapper objectMapper;
    private final TokenProvider tokenProvider;
    private final RedisDao redisDao;
    private final CorsConfig corsConfig;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        // spring security 검사에서 제외할 목록, 사용하기를 권장하지 않음
        return (web) -> web.ignoring().requestMatchers(
//                "/error",
//                "/api-docs/**",
//                "/v1/api-docs/**",
//                "/swagger-ui/**",
//                "/docs/**",
//                "/favicon.ico",
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
                                        "/favicon.ico",
                    "/api/v1/login",
                    "/api/v1/signup"
                ).permitAll()
                .anyRequest().authenticated()
                )


//            .authorizeHttpRequests(auth -> auth.requestMatchers(
//                                    PathRequest.toStaticResources().atCommonLocations()
//                            ).permitAll()
//                            .requestMatchers("/**").permitAll()
//                            .requestMatchers("/auth/login").permitAll()
//                            .requestMatchers("/api/v1/signup").permitAll()
//                            .requestMatchers("/user").hasRole("USER")
//                            .requestMatchers("/admin").hasRole("ADMIN")
//            )
            .addFilterBefore(new CustomAuthenticationFilter(tokenProvider, redisDao), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    public CustomEmailPasswordFilter usernamePasswordAuthenticationFilter() {
//        CustomEmailPasswordFilter filter = new CustomEmailPasswordFilter("/api/v1/login", objectMapper);
//        filter.setAuthenticationSuccessHandler();
//    }
}
