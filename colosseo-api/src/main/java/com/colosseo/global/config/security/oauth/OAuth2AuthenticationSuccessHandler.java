package com.colosseo.global.config.security.oauth;

import com.colosseo.global.config.security.SecurityUtils;
import com.colosseo.global.config.security.UserPrincipal;
import com.colosseo.global.config.security.jwt.TokenProvider;
import com.colosseo.model.user.User;
import com.colosseo.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
//    private final UserRepository userRepository;



    //    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        String targetUrl = determineTargetUrl(request, response, authentication);
//        if (response.isCommitted()) {
//            log.debug("응답이 이미 제출되어 {}로 리다이렉션 할 수 없습니다.", targetUrl);
//            return;
//        }
//
//        clearAuthenticationAttributes(request, response);
//    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        OAuth2CustomUser oAuth2User = (OAuth2CustomUser) authentication.getPrincipal();

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        String email = user.getEmail();

        JSONObject responseJson = new JSONObject();
        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(email);

        responseJson.put("accessToken", accessToken);
        responseJson.put("refreshToken", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().print(responseJson);

    }
    private void redirect(HttpServletRequest request, HttpServletResponse response, String email) throws IOException {
        log.info("redirect method start");
        String accessToken = tokenProvider.createAccessToken(email);
        String refreshToken = tokenProvider.createRefreshToken(email);

//        User user = userRepository.findByEmail(email);
        String uri = createUri(accessToken, refreshToken, email).toString();
    }

    private URI createUri(String accessToken, String refreshToken, String email) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("email", String.valueOf(email));
        queryParams.add("access_token", String.valueOf(accessToken));
        queryParams.add("refresh_token", String.valueOf(refreshToken));

        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .path("/oauth")
                .queryParams(queryParams)
                .build()
                .toUri();
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return true;
    }
}
